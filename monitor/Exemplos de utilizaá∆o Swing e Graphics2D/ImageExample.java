import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class DrawPanel extends JPanel {

    Image img;

    public DrawPanel() {        
        loadImage(); // carrega uma imagem
        Dimension dm = new Dimension(img.getWidth(null), img.getHeight(null)); // define as dimensões do JPanel
        setPreferredSize(dm); // redimensiona o JPanel
    }
    
    private void loadImage() {
        img = new ImageIcon("logoIME.png").getImage(); // abre a imagem a partir de um arquivo
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; // instancia um objeto gráfico 2D
        g2d.drawImage(img, 0, 0, null); // img aponta o objeto da imagem a ser desenhada na tela, 
		                                // sendo (0,0) a origem do sistema (referencial no canto superior esquerdo)
    }

    @Override
    public void paintComponent(Graphics g) { // sobrescreve o método paintComponent do JPanel
        super.paintComponent(g); // desenha a componente
        doDrawing(g); // e renderiza a imagem na componente
    }
}

public class ImageExample extends JFrame {
    public ImageExample() {
        initUI(); // inicia a interface gráfica
    }

    public final void initUI() {
        DrawPanel dpnl = new DrawPanel(); // instancia a classe que é responsável por desenhar no JFrame (classe acima)
        add(dpnl); // adiciona o objeto ao JFrame 
        setTitle("Image"); // define o título do JFrame
        pack(); // está no JFrame, como herança de Window, para redimensioná-lo de acordo com suas componentes
        setLocationRelativeTo(null); // define a posição relativa da janela; se nulo, a janela é centralizada na tela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // define o comportamento do JFrame ao ser fechado
    }

    public static void main(String[] args) {
		// A utilização das componentes Swing, atualmente, dá-se pela instância de threads, como a seguir:
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ImageExample ex = new ImageExample(); // instancia a imagem, o que dispara o construtor da interface gráfica
                ex.setVisible(true); // torna visível o JFrame da imagem
            }
        });
    }
}
