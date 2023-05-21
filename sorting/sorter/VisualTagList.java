package sorting.sorter;

import java.util.Random;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel; 

/**
 * Diese Klasse stellt den Zustand einer Liste von Tag-Objekten grafisch dar.
 * Auf der x-Achse ist die Position in der Liste, auf der y-Achse die Nummer
 * des jeweiligen Tages aufgetragen. Unten links ist die Position 0/0.
 * 
 * Bei Listen bis zu einer Größe von 366 Tagen sind in der sortierten Darstellung
 * alle Tage grün markiert (jeder Tag kommt genau einmal vor). Bei größeren Listen
 * kommen Tage mehrfach vor und die sortierte Ansicht ist nicht mehr grün.
 * 
 * Die Klasse bietet Konstruktoren, die eine neue VisualTagList
 * unsortiert mit Tagen füllen. Mit entsprechenden Konstruktorparametern kann
 * die grafische Darstellung konfiguriert werden.
 * 
 * @author Petra Becker-Pechau
 * @author Lars-Peter Clausen
 * @author Julian Fietkau
 * @author Fredrik Winkler
 * @author Axel Schmolitzky
 * @version 2023
 */
public class VisualTagList implements InPlaceSortableList<Tag>
{
    /**
     * Enthaelt die zu sortierenden Tage.
     */
    private final Tag[] _tag;
    
    /**
     * Enthält eine konstante Verzögerung für die grafische Aktualisierung.
     * Die Verzögerung wird einmalig im Konstruktor abhängig von der 
     * Listenlänge berechnet.
     */
    private final int _verzoegerung;

    private final PunktePanel _panel;

    /**
     * Erzeugt eine neue VisualTagList der Größe 200 und füllt sie
     * unsortiert mit den Tagen von 0 bis laenge-1.
     */
    public VisualTagList()
    {
        this(200);
    }

    /**
     * Erzeugt eine neue VisualTagListe und füllt sie unsortiert mit den Tagen
     * von 0 bis laenge-1. 
     * 
     * Die Verzögerung und die Punktgröße werden abhängig von der gegebenen 
     * Länge berechnet.
     *  
     * @param laenge
     *            die Länge (Kardinalität) der zu erzeugenden Liste. 
     */
    public VisualTagList(int laenge)
    {
        // Die Verzögerung wird größer, wenn die Liste kleiner ist (quadratisch)
        // Die Punkt-Größe ist mindestens 1, ansonsten 400/laenge
        this(laenge, (int) (Math.pow(7-(Math.min(laenge,108.0)/18),2)), Math.max(400 / laenge, 1));
    }

    /**
     * Erzeugt eine neue VisualTagList und füllt sie unsortiert mit den Tagen
     * von 0 bis laenge-1.
     * 
     * @param laenge
     *            die Länge (Kardinalitaet) der zu erzeugenden Liste
     * @param verzoegerung
     *            die Verzögerung in ms
     * @param punktGroesse
     *            jeder Tag-Wert wird durch entsprechend viele Pixel dargestellt
     */
    public VisualTagList(int laenge, int verzoegerung, int punktGroesse)
    {
        _tag = new Tag[laenge];
        for (int i = 0; i < laenge; ++i)
        {
            _tag[i] = new Tag(i % Tag.TAGE_PRO_JAHR) ;
        }
        _verzoegerung = verzoegerung;
        _panel = new PunktePanel(_tag, punktGroesse);
        mischeTage();
    }

    /**
     * Vertauscht die Elemente an den Positionen i und k.
     * 
     * @param i Erste Positionsangabe eines zu vertauschenden Elements.
     * @param k Zweite Positionsangabe eines zu vertauschenden Elements.
     */
    private void swapInternal(int i, int k)
    {
        Tag temp = _tag[i];
        _tag[i] = _tag[k];
        _tag[k] = temp;
    }

    /**
     * Liefert die Anzahl der Tage in der Liste.
     * 
     * @return die Anzahl der Werte in der Liste
     */
    public int size()
    {
        return _tag.length;
    }

    /**
     * Prüft, ob sich in der Liste an der angegebenen Position ein Tag-Objekt
     * befindet.
     * 
     * @param position
     *            die zu prüfende Position
     * @return true, falls (position >= 0) && (position < size())
     */
    public boolean exists(int position)
    {
        return (position >= 0) && (position < size());
    }

    private void pruefe(int position)
    {
        if (!exists(position))
        {
            throw new IndexOutOfBoundsException(String.valueOf(position));
        }
    }
    
    /**
     * Liefert den Tag an der angegebenen Position.
     * 
     * @param position
     *            die Position des Tag-Objekts, das geliefert werden soll
     * @throws IndexOutOfBoundsException
     *             falls !existiert(position)
     * @return der Tag an der angegebenen Position
     */
    public Tag get(int position)
    {
        pruefe(position);

        _panel.selectPoint(position);
        zeichne(position);
        _panel.deselectPoint();
        _panel.zeichnePosition(position);
        return _tag[position];
    }
    
    public void set(int position, Tag t)
    {
        pruefe(position);
        
        _tag[position] = t;

        _panel.selectPoint(position);
        zeichne(position);
        _panel.deselectPoint();
        _panel.zeichnePosition(position);
    }

    /**
     * Vertausche die beiden Elemente an den angegebenen Positionen in der
     * Liste.
     * 
     * @param i
     *            die Position des ersten Elements
     * @param k
     *            die Position des zweiten Elements
     * @throws IndexOutOfBoundsException
     *             falls !(existiert(i) && existiert(k))
     */
    public void swap(int i, int k)
    {
        pruefe(i);
        pruefe(k);

        swapInternal(i, k);

        zeichne(i);
        zeichne(k);
    }
    
    public int compare(int i, int k)
    {
        return _tag[i].compareTo(_tag[k]);
    }

    /**
     * "Sortiert" die Liste aufsteigend, indem einfach jeder Index mit dem
     * passenden Tag mit der Nummer 0..Laenge-1 überschrieben wird.
     */
    public void initialisiereAufsteigend()
    {
        int sup = size();
        for (int i = 0; i < sup; ++i)
        {
            _tag[i] = new Tag(i);
        }
        _panel.repaint();
    }

    /**
     * "Sortiert" die Liste absteigend, indem einfach jeder Index mit dem
     * passenden Tag mit der Nummer Laenge-1..0 überschrieben wird.
     */
    public void initialisiereAbsteigend()
    {
        int sup = size();
        for (int i = 0; i < sup; ++i)
        {
            _tag[i] = new Tag(sup - 1 - i);
        }
        _panel.repaint();
    }

    /**
     * Sortiert die Liste so, dass ihre Einträge tendenziell zwischen hohen und
     * niedrigen Werten hin und her springen.
     */
    public void initialisiereAlternierend()
    {
        int sup = size();
        for (int i = 0; i < sup; i += 2)
        {
            _tag[i] = new Tag(i);
        }
        for (int i = 1, k = (sup | 1) - 2; i < sup; i += 2, k -= 2)
        {
            _tag[i] = new Tag(k);
        }
        _panel.repaint();
    }

    /**
     * Initialisiert die Liste so, dass alle Einträge den gleichen Wert haben.
     */
    public void initialisiereGleich()
    {
        int sup = size();
        Tag mittelTag = new Tag(sup/2);
        for (int i = 0; i < sup; ++i)
        {
            _tag[i] = mittelTag;
        }
        _panel.repaint();
    }

    /**
     * Permutiert diese Liste so, dass die Einträge zufällig angeordnet
     * sind. Die Liste sieht nach jedem Aufruf anders aus.
     */
    public void mischeTage()
    {
        Random zufall = new Random();
        for (int i = size() - 1; i > 0; --i)
        {
            swapInternal(i, zufall.nextInt(i));
        }
        _panel.repaint();
    }

    /**
     * Zeichnet die gegebene Position neu und wartet kurz
     */
    private void zeichne(int position)
    {
        _panel.zeichnePosition(position);
        try
        {
            Thread.sleep(_verzoegerung);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Dieses Panel dient zur Darstellung eines Tag-Arrays in einem
     * Koordinatensystem.
     * 
     * Auf der X-Achse werden die Indizes aufgetragen, auf der Y-Achse die
     * Nummern der Tage. Es wird davon ausgegangen, dass das Array die Tage 
     * mit der Nummer von 0 bis laenge-1 enthält. Für das Setzen eines Wertes 
     * wird eine zusätzliche zeichne-Methode angeboten, um die Performanz zu erhöhen.
     */
    private static class PunktePanel extends JPanel
    {
        private static final Color BACKGROUND_COLOR = Color.BLACK;
        private static final Color POINT_COLOR = Color.WHITE;
        private static final Color SELECTED_POINT_COLOR = Color.RED;
        private static final Color FINAL_POINT_COLOR = Color.GREEN;
        private static final int RAND = 4;

        private final Tag[] _tagArray;
        private final int _punktGroesse;
        private final int _ausdehnung;
        private int _selected;

        private final JFrame _frame;

        /**
         * Erzeugt ein neues PunktePanel.
         * 
         * @param tagArray Die anzuzeigenden Tage.
         * @param punktGroesse Pixelanzahl, die zur Anzeige eines Punktes genutzt werden.
         */
        public PunktePanel(Tag[] tagArray, int punktGroesse)
        {
            _tagArray = tagArray;
            _punktGroesse = punktGroesse;
            _ausdehnung = tagArray.length * punktGroesse;
            _selected = -1;

            // Initialisere dieses Panel
            setBackground(BACKGROUND_COLOR);
            int groesse = RAND + _ausdehnung + RAND;
            setPreferredSize(new Dimension(groesse, groesse));

            // Initialisiere das verwendete JFrame und setze this (Panel-Objekt) als content pane.
            _frame = new JFrame("Tag-Liste (" + tagArray.length + ")");
            _frame.setResizable(false);
            _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            _frame.getContentPane().add(this);
            _frame.setBackground(BACKGROUND_COLOR);
            _frame.pack();
            _frame.setVisible(true);
        }

        /**
         * Liefert die Pixelkoordinate für die übergebene Position.
         * 
         * @param position Eine Position deren Pixelkoordinate geliefert werden soll.
         * @return eine Pixelkoordinate für die angegebene Position.
         */
        private int screenAt(int position)
        {
            return RAND + position * _punktGroesse;
        }

        /**
         * Zeichnet einen Punkt mit der entsprechenden Punktgröße auf den Bildschirm.
         * 
         * @param x x-Koordinate des Punkts
         * @param y y-Koordinate des Punkts
         * @param color Die zu verwendende Farbe
         * @param g Ein zu verwendendes Graphics-Objekt auf dem gezeichnet werden soll
         */
        private void zeichnePunkt(int x, int y, Color color, Graphics g)
        {
            y = _tagArray.length - 1 - y;
            g.setColor(color);
            g.fillRect(screenAt(x), screenAt(y), _punktGroesse, _punktGroesse);
        }

        /**
         * Zeichnet die angegebene Position.
         * @param x Eine Position
         */
        public void zeichnePosition(int x)
        {
            repaint(screenAt(x), RAND, _punktGroesse, _ausdehnung);
        }

        /**
         * Zeichnet das Panel.
         * @param g Ein Graphics-Objekt auf dem gezeichnet werden soll.
         */
        public void paint(Graphics g)
        {
            super.paint(g);
            for (int i = 0; i < _tagArray.length; ++i)
            {
                Color color = POINT_COLOR;
                if (i == _selected)
                {
                    color = SELECTED_POINT_COLOR;
                }
                else if (i == _tagArray[i].nummer())
                {
                    color = FINAL_POINT_COLOR;
                }
                zeichnePunkt(i, _tagArray[i].nummer(), color, g);
            }
        }

        /**
         * Selektiert eine Position, so dass diese hervorgehoben gezeichnet wird.
         * 
         * @param position Eine Position, die selektiert werden soll.
         */
        public void selectPoint(int position)
        {
            _selected = position;
        }

        /**
         * Deselektiert die augenblicklich selektierte Position.
         */
        public void deselectPoint()
        {
            _selected = -1;
        }

        private static final long serialVersionUID = -1621544717859097232L;
    }
}
