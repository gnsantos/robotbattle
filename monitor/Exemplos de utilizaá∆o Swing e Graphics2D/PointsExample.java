import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class DrawPanel extends JPanel {

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; // instancia o contexto gráfico 2D como o contexto atual 
        g2d.setColor(Color.blue); // define a cor azul para o contexto (ela será a cor padrão de todos os objetos criados no contexto)

        for (int i = 0; i <= 1000; i++) {
            Dimension size = getSize();  // obtém as dimensões do JPanel
            Insets insets = getInsets(); // obtém os tamanhos de cada borda do container
            int w = size.width - insets.left - insets.right; // calcula a largura descontando as bordas 
            int h = size.height - insets.top - insets.bottom; // idem para a altura
            Random r = new Random(); // sorteia um número aleatório, utilizando de uma semente com baixa probabilidade de repetição
            int x = Math.abs(r.nextInt()) % w; // coordenada aleatória x (interna ao container)
            int y = Math.abs(r.nextInt()) % h; // coordenada aleatória y (interna ao container)
            g2d.drawLine(x, y, x, y); // os dois primeiros argumentos são (x,y) da origem da linha e os dois últimos são (x,y) do final da linha
			                          // (pela igualdade dos argumentos, teremos um ponto desenhado no container de cor azul)
        }
    }

    @Override
    public void paintComponent(Graphics g) { // sobrescreve o método PaintComponent
        super.paintComponent(g);
        doDrawing(g); // dispara o desenho no JPanel
    }
}

public class PointsExample extends JFrame {

    public PointsExample() {
        initUI(); // o construtor dispara a inicialização da interface gráfica
    }

    public final void initUI() {
        DrawPanel dpnl = new DrawPanel(); //instancia o DrawPanel (classe acima)
        add(dpnl); // adiciona o DrawPanel ao JFrame
        setSize(500, 400); // define as dimensões do JFrame
        setTitle("Points"); // define o título do JFrame
        setLocationRelativeTo(null); // define a localização relativa do JFrame na tela (se null, centraliza na tela)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // comportamento padrão ao fechar o JFrame
    }

    public static void main(String[] args) {
		// instância das componentes do Swing a partir de uma thread de interface gráfica específica (como no Image Example)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PointsExample ex = new PointsExample(); // instancia a classe de exemplo, que inicia a interface gráfica
                ex.setVisible(true); // exibe o JFrame na tela
            }
        });
    }
}
