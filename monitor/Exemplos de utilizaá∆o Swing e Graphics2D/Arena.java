import java.awt.*;
import java.awt.event.*;
import java.awt.TexturePaint;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

class Celula { // vejam comentários na classe Celula em World.java -- esta é idêntica!
	Polygon p = new Polygon();
	BufferedImage ime;
	Graphics2D Gime;

	Celula(int x, int y, int r, BufferedImage t) {
		ime = t;

		for (int i = 0; i < 6; i++)
			p.addPoint(x + (int) (r * Math.sin(i * 2 * Math.PI / 6)),
					   y + (int) (r * Math.cos(i * 2 * Math.PI / 6)));
		
		Gime = ime.createGraphics();
	}

	void draw(Graphics g) { 
		Graphics2D g2d = (Graphics2D) g;
		Rectangle r = new Rectangle(0,0,100,100);
		g2d.setPaint(new TexturePaint(ime, r));
		g2d.fill(p);
	}	

	void trans(int dx, int dy) {
		p.translate(dx, dy);
	}
}

class Campo extends JPanel {
	Celula[][] cel = new Celula[10][10]; // define a matriz de células do terreno (mapa do jogo)
	int Larg, Alt, Dx, Dy; // largura do terreno, altura do terreno, incremento em x e incremento em y
	BufferedImage grama, terra, agua; // texturas a serem carregadas para o terreno

	int[][] Terreno = { // Mapa do terreno: cada valor estará vinculado ao array Textura (definido adiante)
	                    // Sugestão: carreguem o mapa de um arquivo texto!  
		{ 0, 0, 0, 1, 2, 2, 2, 2, 1, 1},
		 { 0, 0, 1, 1, 2, 2, 2, 2, 1, 1},
		{ 0, 0, 1, 2, 2, 2, 0, 2, 1, 1},
		 { 0, 0, 1, 1, 1, 2, 2, 2, 2, 2},
		{ 0, 0, 0, 0, 1, 2, 2, 2, 2, 2},
		 { 0, 0, 0, 1, 0, 2, 2, 2, 2, 2},
		{ 0, 0, 1, 1, 0, 0, 0, 2, 2, 1},
		 { 0, 0, 1, 1, 0, 0, 2, 2, 2, 1},
		{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 1},
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
	};

	Campo(int L, int W, int H) {
		Dx = (int) (2 * L * Math.sin(2 * Math.PI / 6)); // incremento em x para desenhar os hexágonos
		Dy = 3* L/2; // idem para y
		Larg = W; Alt = H;

		// cada try..catch que segue carregará uma textura, ou levantará uma exceção que encerrará a aplicação com erro
		try {
			grama = ImageIO.read(this.getClass().getResource("grama.png"));
		}
		catch (Exception e) {
			System.exit(1);
		}

		try {
			terra = ImageIO.read(this.getClass().getResource("terra.png"));
		}
		catch (Exception e) {
			System.exit(1);
		}

		try {
			agua = ImageIO.read(this.getClass().getResource("agua.png"));
		}
		catch (Exception e) {
			System.exit(1);
		}

		BufferedImage[] Textura = {agua, terra, grama}; // array de texturas (valores de enumeração: 0, 1, 2)

		int DELTA = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				// instância das células hexagonais, com as texturas adequadas, e atribuição destas ao mapa (a ser renderizado em paintComponent)
				cel[i][j] = new Celula(DELTA + L + i*Dx, L + j*Dy, L, Textura[Terreno[j][i]]); 
				DELTA = DELTA == 0 ? Dx/2 : 0;				
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < 10; i++) 
			for (int j = 0; j < 10; j++)
				cel[i][j].draw(g); // pinta as células no contexto gráfico
	}
}

public class Arena extends Frame { // vejam comentários na classe ImageExample -- esta é muito semelhante!
	public Arena() {
		setTitle("Polygon");
		setSize(600, 600);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		add(new Campo(30, 600, 600));
		setVisible(true);
	}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Arena ar = new Arena();
                ar.setVisible(true);
            }
        });
    }
}
