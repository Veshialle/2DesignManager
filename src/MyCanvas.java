import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Vesh on 02/09/2017.
 */
public class MyCanvas extends JComponent{

    private static final long serialVersionUID = 1L;

    public List<Figura> getArray() {
        return array;
    }

    private List<Figura> array;

    private boolean gridFlag;

    public void setGridFlag(boolean flag) { this.gridFlag = flag; }

    public boolean getGridFlag() { return this.gridFlag; }

    public void setArray(List<Figura> array) {
        this.array = array;
    }

    public MyCanvas(){ this.setGridFlag(false);};

    public MyCanvas(List<Figura> fig) {
        super();
        this.array = fig;
    }
    public void paintImage() {
        Graphics d = getGraphics();
        d.clearRect(0, 0, this.getWidth(), this.getHeight()); // pulisce il canvas
        for (Figura i : this.getArray()) { // per ogni figura nella lista di figure
            if (i.visibile == true) {// se la figura e' visibile
                i.draw(d);// chiama il metodo "disegna" della figura
            }
        }
        if(this.getGridFlag()){
            int k;
            int dimCell = 20;
            int width = getWidth();
            int height = getHeight();
            int carryC = 0, carryR = 0;
            if(width%dimCell!=0) carryC =1;
            int columns = (width / dimCell) + carryC;
            if(height % dimCell != 0) carryR = 1;
            int rows = (height / dimCell) + carryR;
            for (k = 0; k < rows; k++)
                d.drawLine(0, k * dimCell , width, k * dimCell );

            for (k = 0; k < columns; k++)
                d.drawLine(k*dimCell , 0, k*dimCell , height);
            // Nel caso si voglia fare una cosa piu` bella esteticamente, basta partire dal centro con la costruzione
            // della griglia e non dall'angolo in alto a sinistra
        }
        this.validate();
    }

}
