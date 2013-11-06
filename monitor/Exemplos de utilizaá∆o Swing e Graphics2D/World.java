import java.awt.*;
import java.awt.event.*;
import java.awt.TexturePaint;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class Celula {
	Polygon p = new Polygon();
	BufferedImage ime;
	Graphics2D Gime;

	Celula(int x, int y, int r) {
		try {
			ime = ImageIO.read(this.getClass().getResource("logoIME.png")); // lê a imagem do disco para o objeto ime
		}
		catch (Exception e) {
			System.exit(1); // caso a leitura não seja bem-sucedida, finaliza a aplicação com erro
		}
		
		for (int i = 0; i < 6; i++)
			// cada célula é hexagonal e o polígono será definido a partir de seus vértices
			// para tanto, a partir da origem (x,y), definem-se os vértices a partir de rotações de 60º
			p.addPoint((int) (x + r * Math.cos(i * 2 * Math.PI / 6)),
					   (int) (y + r * Math.sin(i * 2 * Math.PI / 6))); 
		
		Gime = ime.createGraphics(); // atribui a imagem carregada ao contexto gráfico instanciado
	}
	
	void draw(Graphics g) { 
		Graphics2D g2d = (Graphics2D) g; // instancia um contexto gráfico 2D
		Rectangle r = new Rectangle(200,200,20,20); // os dois primeiros argumentos são a origem do retângulo (referencial no canto 
		                                            // superior esquerdo), e os dois seguintes são largura e altura do retângulo
													// neste retângulo será pintada a textura que preencherá a célula
		g2d.setPaint(new TexturePaint(ime, r)); // define o que será pintado no contexto gráfico 2D, isto é, a textura (logo do IME) no retângulo
		g2d.fill(p); // preenche o contexto gráfico
	}

	void trans(int dx, int dy) {
		p.translate(dx, dy); // translação na horizontal e vertical, respectivamente, do polígono
	}
}

public class World extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		Celula cel = new Celula(100, 100, 50); // instancia uma célula

		for(int i = 0; i < 4; i++)  {
			cel.draw(g); // desenha a célula no contexto gráfico
			cel.trans(100,0); // faz a translação da célula na tela
		}
		cel.trans(-400, 100);
		for(int i = 0; i < 4; i++)  {
			cel.draw(g);
			cel.trans(100,0);
		}
		cel.draw(g);
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame(); // observe a diferença na forma de instanciar no ImageExample.java
		frame.setTitle("Polygon"); // definição de título do JFrame
		frame.setSize(600, 600); // definição do tamanho do JFrame
		frame.addWindowListener(new WindowAdapter() { // adiciona um ouvinte (listener), que possibilita aguardar eventos da interface gráfica
			public void windowClosing(WindowEvent e) { // ao fechar a janela
				System.exit(0); // encerra a aplicação normalmente
			}
		});
		Container contentPane = frame.getContentPane(); // instancia um container no JFrame
		contentPane.add(new World()); // cria o mundo, que irá gerar as células no container
		frame.setVisible(true); // exibe o JFrame na tela
	}
}
