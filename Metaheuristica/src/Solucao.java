import java.util.ArrayList;
import java.util.Random;

public class Solucao {

	private String[] clientesXFacilidade;
	private Integer custoTotal = Integer.MAX_VALUE;
	ArrayList<String> facilidadesUsadas = new ArrayList<String>();

	public ArrayList<String> getFacilidadesUsadas() {
		return facilidadesUsadas;
	}

	public void AddFacilidadesUsada(String facilidadeNova) {
		boolean jaPossui = false;
		for (String facilidade : this.facilidadesUsadas) {
			if (facilidade.equals(facilidadeNova)) {
				jaPossui = false;
			}
		}
		if (!jaPossui) {
			this.facilidadesUsadas.add(facilidadeNova);
		}
	}

	public void removeFacilidadesUsada(String facilidade) {
		this.facilidadesUsadas.remove(facilidade);
		for (int i = 0; i < clientesXFacilidade.length; i++) {
			if (clientesXFacilidade[i] != null && clientesXFacilidade[i].equals(facilidade)) {
				clientesXFacilidade[i] = null;
			}
		}
	}

	public void setFacilidadesUsadas(ArrayList<String> facilidadesUsadas) {
		this.facilidadesUsadas = facilidadesUsadas;
	}

	public String[] getClientesXFacilidade() {
		return clientesXFacilidade;
	}

	public void setClientesXFacilidade(String[] clientesXFacilidade) {
		this.clientesXFacilidade = clientesXFacilidade;
	}

	public Integer getCustoTotal() {
		return custoTotal;
	}

	public void setCustoTotal(Integer custoTotal) {
		this.custoTotal = custoTotal;
	}

	public void ConstruirSolucao(Integer[][] custoConexao,
			Integer[] custoAberturaFacilidade, Random random) {
		this.custoTotal = 0;
		this.clientesXFacilidade = new String[custoConexao[0].length];
		Integer iter = 0;
		while (iter < custoAberturaFacilidade.length / 2) {
			String facilidade = ""
					+ random.nextInt(custoAberturaFacilidade.length-1);
			AddFacilidadesUsada(facilidade);
			iter++;
		}
		setCidadesSemFacilidade(custoConexao, custoAberturaFacilidade);
		removeFacilidadesNaoUsadas(custoConexao, custoAberturaFacilidade);
	}

	public void setCidadesSemFacilidade(Integer[][] custoConexao,
			Integer[] custoAberturaFacilidade) {

		for (Integer i = 0; i < this.clientesXFacilidade.length; i++) {
			if (clientesXFacilidade[i] == null) {
				Integer linhaMenorCusto = 0;
				Integer menorCusto = Integer.MAX_VALUE;
				for (String facilidadesUsada : facilidadesUsadas) {
					if (custoConexao[Integer.parseInt(facilidadesUsada)][i] < menorCusto) {
						menorCusto = custoConexao[Integer
								.parseInt(facilidadesUsada)][i];
						linhaMenorCusto = Integer.parseInt(facilidadesUsada);
					}
				}
				this.clientesXFacilidade[i] = "" + linhaMenorCusto;
			}
		}
		this.custoTotal = calculaCustoTotal(custoAberturaFacilidade,
				custoConexao);
	}

	public void setCidadesXFacilidades(Integer[][] custoConexao,
			Integer[] custoAberturaFacilidade) {

		for (Integer i = 0; i < this.clientesXFacilidade.length; i++) {
			Integer linhaMenorCusto = 0;
			Integer menorCusto = Integer.MAX_VALUE;
			for (String facilidadesUsada : facilidadesUsadas) {
				if (custoConexao[Integer.parseInt(facilidadesUsada)][i] < menorCusto) {
					menorCusto = custoConexao[Integer
							.parseInt(facilidadesUsada)][i];
					linhaMenorCusto = Integer.parseInt(facilidadesUsada);
				}
			}
			this.clientesXFacilidade[i] = "" + linhaMenorCusto;

		}
		this.custoTotal = calculaCustoTotal(custoAberturaFacilidade,
				custoConexao);
	}

	public void removeFacilidadesNaoUsadas(Integer[][] custoConexao,
			Integer[] custoAberturaFacilidade) {
		ArrayList<String> facilidadesRemover = new ArrayList<String>();
		for (String facilidadesUsada : facilidadesUsadas) {
			boolean achou = false;
			for (String facilidade : clientesXFacilidade) {
				if (facilidade.equals(facilidadesUsada)) {
					achou = true;
				}
			}
			if (!achou) {
				facilidadesRemover.add(facilidadesUsada);
			}
		}
		for (String facilidade : facilidadesRemover) {
			removeFacilidadesUsada(facilidade);
		}
	}

	public void imprimiSolucao() {
		System.err.println("clientesXFacilidade");
		for (int i = 0; i < clientesXFacilidade.length; i++) {
			System.err.println(i + 1 + " | " + (Integer.parseInt(clientesXFacilidade[i])+1));
		}
		System.err.println("facilidadesUsadas");
		for (int i = 0; i < facilidadesUsadas.size(); i++) {
			System.err.println((Integer.parseInt(facilidadesUsadas.get(i))+1));
		}
	}

	@SuppressWarnings("unchecked")
	public Solucao copiarSolucao() {
		Solucao nova = new Solucao();
		nova.setClientesXFacilidade(this.clientesXFacilidade.clone());
		nova.setCustoTotal(this.custoTotal);
		nova.setFacilidadesUsadas((ArrayList<String>) this.facilidadesUsadas
				.clone());
		return nova;
	}

	public Integer calculaCustoTotal(Integer[] custoAberturaFacilidade,
			Integer[][] custoConexao) {
		Integer custoTotal = 0;
		for (String facilidade : facilidadesUsadas) {
			custoTotal += custoAberturaFacilidade[Integer.parseInt(facilidade)];
		}
		for (int i = 0; i < clientesXFacilidade.length; i++) {
			custoTotal += custoConexao[Integer.parseInt(clientesXFacilidade[i])][i];
		}
		return custoTotal;
	}

	public void reconstroiSolucao(int quantidade, Integer numFacilidades,
			Random random) {
		ArrayList<String> facilidadesNaoUsadas = new ArrayList<String>();
		for (int i = 0; i < numFacilidades; i++) {
			facilidadesNaoUsadas.add(i + "");
		}
		for (String facilidadeUsada : facilidadesUsadas) {
			facilidadesNaoUsadas.remove(facilidadeUsada);
		}
		for (int i = 0; i < quantidade; i++) {
			AddFacilidadesUsada(facilidadesNaoUsadas.get(random
					.nextInt(facilidadesNaoUsadas.size() -1)));
		}

	}
	public void verificaNullclientesXFacilidade(){
		for (String facilidade : clientesXFacilidade) {
			if(facilidade == null){
				System.err.println("clientesXFacilidade possui null.");
			}
		}
	}
	
}
