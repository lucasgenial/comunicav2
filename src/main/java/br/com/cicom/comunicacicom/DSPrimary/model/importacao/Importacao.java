package br.com.cicom.comunicacicom.DSPrimary.model.importacao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Bairro;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Endereco;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.OcorrenciaLog;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom10LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom10Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom11LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom11Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom12LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom12Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom13LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom13Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom14LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom14Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom15LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom15Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom16LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom16Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom17LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom17Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom18LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom18Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom19LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom19Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom1LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom1Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom20LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom20Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom22LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom22Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom23LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom23Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom2LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom2Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom3LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom3Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom4LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom4Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom5LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom5Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom6LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom6Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom7LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom7Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom8LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom8Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom9LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom9Service;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.BairroService;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.CidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.LocalidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.EstadoOcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.TipificacaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.EstabelecimentoService;

public interface Importacao {
	
	default Cecoco importa(Long id, Long estabelecimento, Cicom10Service serviceCicom10, Cicom11Service serviceCicom11,
			Cicom12Service serviceCicom12, Cicom13Service serviceCicom13, Cicom14Service serviceCicom14, Cicom15Service serviceCicom15,
			Cicom16Service serviceCicom16, Cicom17Service serviceCicom17, Cicom18Service serviceCicom18, Cicom19Service serviceCicom19,
			Cicom1Service serviceCicom1, Cicom20Service serviceCicom20, Cicom22Service serviceCicom22, Cicom23Service serviceCicom23,
			Cicom2Service serviceCicom2, Cicom3Service serviceCicom3, Cicom4Service serviceCicom4, Cicom5Service serviceCicom5,
			Cicom6Service serviceCicom6, Cicom7Service serviceCicom7, Cicom8Service serviceCicom8, Cicom9Service serviceCicom9) {
			Cecoco retorno = new Cecoco();
			retorno.setId((long) 0);
		try {
			switch (estabelecimento.intValue()) {
				case 1:
					// CICOM 1 CAATINGA JUAZEIRO
					return (Cecoco) serviceCicom1.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 2:
					// CICOM 2 CENTRO SUL VITÓRIA DA CONQUISTA
					return (Cecoco) serviceCicom2.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 3:
					// CICOM 3 CERRADO BARREIRAS
					return (Cecoco) serviceCicom3.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 4:
					// CICOM 4 CHAPADA - ITABERABA
					return (Cecoco) serviceCicom4.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 5:
					// CICOM 5 COSTA DO DENDE - VALENÇA
					return (Cecoco) serviceCicom5.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 6:
					// CICOM 6 COSTA DO DESCOBRIMENTO - PORTO SEGURO
					return (Cecoco) serviceCicom6.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 7:
					// CICOM 7 EXTREMO SUL TEIXEIRA DE FREITAS
					return (Cecoco) serviceCicom7.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 8:
					// CICOM 8 LITORAL NORTE ESPLANADA
					return (Cecoco) serviceCicom8.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 9:
					// CICOM 9 OESTE - SANTA MARIA DA VITORIA
					return (Cecoco) serviceCicom9.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 10:
					// CICOM 10 PORTAL DO SERTÃO - FEIRA DE SANTANA
					return (Cecoco) serviceCicom10.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 11:
					// CICOM 11 RECONCAVO - SANTO ANTONIO DE JESUS
					return (Cecoco) serviceCicom11.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 12:
					// CICOM 12 REGIÃO CACAUEIRA - ITABUNA
					return (Cecoco) serviceCicom12.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 13:
					// CICOM 13 REGIÃO CENTRAL - JEQUIÉ
					return (Cecoco) serviceCicom13.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 14:
					// CICOM 14 REGIÃO SISALEIRA - SERRINHA
					return (Cecoco) serviceCicom14.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 15:
					// CICOM 15 REGIONAL CENTRO NORTE - IRECÊ
					return (Cecoco) serviceCicom15.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 16:
					// CICOM 16 REGIÃO LESTE - ALAGOINHAS
					return (Cecoco) serviceCicom16.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 17:
					// CICOM 17 REGIONAL NORDESTE EUCLIDES DA CUNHA
					return (Cecoco) serviceCicom17.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 18:
					// CICOM 18 REGIONAL NORTE - PAULO AFONSO
					return (Cecoco) serviceCicom18.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 19:
					// CICOM 19 REGIONAL SUDOESTE - GUANAMBI
					return (Cecoco) serviceCicom19.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 20:
					// CICOM 20 REGIONAL VELHO CHICO - IBOTIRAMA
					return (Cecoco) serviceCicom20.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 22:
					// CICOM 22 SEMIARIDO - SENHOR DO BONFIM
					return (Cecoco) serviceCicom22.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
				case 23:
					// CICOM 23 SERTÃO PRODUTIVO BRUMADO
					return (Cecoco) serviceCicom23.buscaPorIdeEstabelecimento(id, estabelecimento.longValue()).get(0);
			}		
		} catch (Exception e) {
//			System.out.println("Importação - Erro: " + e.getMessage());
//			System.out.println(e);
			return retorno;
		}
		return retorno;
	}

	default Ocorrencia converte(TipificacaoService serviceTipificacao, EstadoOcorrenciaService serviceEstadoOcorrencia,
			CidadeService serviceCidade, LocalidadeService serviceLocalidade, BairroService serviceBairro,
			EstabelecimentoService serviceEstabelecimento, Cecoco importa) {
		Ocorrencia ocorrencia = new Ocorrencia();

		try {
			Integer idCidade = 0, idLocalidade = 0, idBairro = 0;
//			System.out.print("Convertendo: " + importa.getId());
			ocorrencia.setSic(Long.toString(importa.getId()));
			ocorrencia.setTipificacao(serviceTipificacao.buscaPorNome(importa.getTipo()));
			ocorrencia.setDataOcorrencia(LocalDateTime.of(importa.getDatainicio(), importa.getHorainicio()));
			ocorrencia.setDataUltimaModificao(LocalDateTime.now());
			ocorrencia.setDataDaCriacao(LocalDateTime.now());
			ocorrencia.setEstadoOcorrencia(serviceEstadoOcorrencia.buscaPorId(importa.getEstado() + 1).get());
			if (!importa.getDescricao().isEmpty()) {
				ocorrencia.setDescricao(importa.getDescricao());
//				System.out.print(" -->Descrição ");
			}
			if (!importa.getHistorico().isEmpty()) {
				ocorrencia.setHistorico(importa.getHistorico());
//				System.out.print(" -->Histórico ");
			}
			Endereco endereco = new Endereco();
			if (!importa.getEndereco().isEmpty()) {
				endereco.setRua(importa.getEndereco());
//				System.out.print(" -->Logradouro ");
			}
			if (!importa.getPontoreferencia().isEmpty()) {
				endereco.setReferencia(importa.getPontoreferencia());
//				System.out.print(" -->Referencia ");
			}
			if (!importa.getCidade().isEmpty()) {
//				System.out.println("Verificando a Cidade");
				String cidadeStr = limpaFrase(importa.getCidade());
				List<Cidade> cidades = new ArrayList<>();
				cidades = serviceEstabelecimento.listaDeCidades(importa.getEstabelecimento());
				int c = 0;
				while (c <= cidades.size() - 1) {
					if (cidades.get(c).getNome().equals(cidadeStr)) {
						//endereco.setCidade(cidades.get(c));
						idCidade = cidades.get(c).getId().intValue();
//						System.out.println(" -->Cidade " + idCidade + "/" + cidades.get(c).getNome());
						break;
					}
					c++;
				}
				String localidadeStr = limpaFrase(importa.getDistrito());
				String bairroStr = limpaFrase(importa.getBairro());
				
				if ( !bairroStr.isEmpty() && !idCidade.equals(0)) {
//					List<Bairro> bairros = new ArrayList<>(serviceBairro.buscaPorCidade(serviceCidade.buscaPorId(idCidade.longValue()).get()));
//					System.out.println(bairros.size());
//					int b = 0;
//					while (b <= bairros.size()-1) {
//						if( bairros.get(b).getNome().equals( bairroStr ) ) {
//							//endereco.setBairro( bairros.get(b));
//							idBairro = bairros.get(b).getId().intValue();
//							System.out.println(" -->Bairro " + idBairro + "/" + bairros.get(b));
//							break;
//						}
//						b++;
//					}
					if ( !localidadeStr.isEmpty() && !idBairro.equals(0) ) {
//						System.out.println("Verificando a localidade");
						List<Localidade> localidades = new ArrayList<>( serviceCidade.buscaPorId(idCidade.longValue()).get().getLocalidades() );
//						System.out.println(localidades.size());
						int l = 0;
						while (l <= localidades.size() - 1) {
							if ( localidades.get(l).getNome().equals(localidadeStr) && localidades.get(l).getBairros().get(idBairro).getNome().equals(bairroStr) ) {
								idLocalidade = localidades.get(l).getId().intValue();
								endereco.setBairro(localidades.get(l).getBairros().get(idBairro));
								endereco.setCidade(endereco.getBairro().getLocalidade().getCidade());
//								System.out.println(endereco);
								break;
							}
							l++;
						}
					} else
						if (localidadeStr.isEmpty()) {
//							System.out.println("Sem localidade");
							List<Localidade> localidades = new ArrayList<>(  serviceCidade.buscaPorId(idCidade.longValue()).get().getLocalidades()  );
//							System.out.println(localidades.size());
							int l = 0;
							while (l <= localidades.size() - 1) {
								if (localidades.get(l).getNome().equals("SEDE") ) {
									//endereco.getBairro().setLocalidade(localidades.get(l));
									idLocalidade = localidades.get(l).getId().intValue();
//									System.out.println(" -->Localidade " + idLocalidade + "/" + localidades.get(l).getNome());
									List<Bairro> bairros = new ArrayList<>( localidades.get(l).getBairros() );
									bairros = localidades.get(l).getBairros();
									int b = 0;
									while (b <= bairros.size()-1) {
										if( bairros.get(b).getNome().equals( bairroStr) ) {
											endereco.setBairro( bairros.get(b));
											endereco.setCidade(endereco.getBairro().getLocalidade().getCidade());
											idBairro = bairros.get(b).getId().intValue();
//											System.out.println(endereco);
											break;
										}
										b++;
									}
									break;
								}
								l++;
							}
						}
				}
			}
			ocorrencia.setEndereco(endereco);
//			System.out.println("Converção concluida: " + ocorrencia);
			return ocorrencia;
		} catch (Exception e) {
//			System.out.println("Converção Erro: " + e.getMessage());
//			System.out.println(e);
			return ocorrencia;
		}
	}

	default List<CecocoLog> importaLog(Long numeroocorrencia, Estabelecimento estabelecimento, 
									Cicom1LogService serviceCicom1Log,
									Cicom2LogService serviceCicom2Log,
									Cicom3LogService serviceCicom3Log,
									Cicom4LogService serviceCicom4Log,
									Cicom5LogService serviceCicom5Log,	
									Cicom6LogService serviceCicom6Log,
									Cicom7LogService serviceCicom7Log,
									Cicom8LogService serviceCicom8Log,
									Cicom9LogService serviceCicom9Log,
									Cicom10LogService serviceCicom10Log,
									Cicom11LogService serviceCicom11Log,
									Cicom12LogService serviceCicom12Log,
									Cicom13LogService serviceCicom13Log,
									Cicom14LogService serviceCicom14Log,
									Cicom15LogService serviceCicom15Log,
									Cicom16LogService serviceCicom16Log,
									Cicom17LogService serviceCicom17Log, 
									Cicom18LogService serviceCicom18Log,
									Cicom19LogService serviceCicom19Log, 
									Cicom20LogService serviceCicom20Log,
									Cicom22LogService serviceCicom22Log, 
									Cicom23LogService serviceCicom23Log ) {
		List<CecocoLog> cecocoLog = new ArrayList<CecocoLog>();
		
		try {
			switch (estabelecimento.getId().intValue()) {
			case 1:
				return (List<CecocoLog>) serviceCicom1Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 2:
				return (List<CecocoLog>) serviceCicom2Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 3:
				return (List<CecocoLog>) serviceCicom3Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 4:
				return (List<CecocoLog>) serviceCicom4Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 5:
				return (List<CecocoLog>) serviceCicom5Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 6:
				return (List<CecocoLog>) serviceCicom6Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 7:
				return (List<CecocoLog>) serviceCicom7Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 8:
				return (List<CecocoLog>) serviceCicom8Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 9:
				return (List<CecocoLog>) serviceCicom9Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 10:
				return (List<CecocoLog>) serviceCicom10Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 11:
				return (List<CecocoLog>) serviceCicom11Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 12:
				return (List<CecocoLog>) serviceCicom12Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 13:
				return (List<CecocoLog>) serviceCicom13Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 14:
				return (List<CecocoLog>) serviceCicom14Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 15:
				return (List<CecocoLog>) serviceCicom15Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 16:
				return (List<CecocoLog>) serviceCicom16Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 17:
				return (List<CecocoLog>) serviceCicom17Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 18:
				return (List<CecocoLog>) serviceCicom18Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 19:
				return (List<CecocoLog>) serviceCicom19Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 20:
				return (List<CecocoLog>) serviceCicom20Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 22:
				return (List<CecocoLog>) serviceCicom22Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			case 23:
				return (List<CecocoLog>) serviceCicom23Log.buscaTodosPorNumeroocorrencia(numeroocorrencia);
			}
		} catch (Exception e) {
//			System.out.println("Importa Log - Erro: " + e);
		}
		return cecocoLog;	
		}
	
	default List<OcorrenciaLog> convertLog(List<CecocoLog> cecocolog) {
		List<OcorrenciaLog> listaocorrenciaLog = new ArrayList<OcorrenciaLog>();
		try {
			for(CecocoLog _cecocoLog : cecocolog) {
				OcorrenciaLog log = new OcorrenciaLog();
				log.setData(_cecocoLog.getData());
				log.setDescricao(_cecocoLog.getDescricao());
				log.setOperador(_cecocoLog.getOperador());
				log.setAnotacao(_cecocoLog.getAnotacao());
				listaocorrenciaLog.add(log);
			}
		} catch (Exception e) {
//			System.out.println("Converte Log - Erro: " + e.getMessage());
		}
		return listaocorrenciaLog;
	}

	default String limpaFrase(String frase) {
		frase = frase.toUpperCase();
		String frase_nova = "";
		int t = 0;
		t = frase.length() - 1;
		if (frase.substring(t, t + 1).equals(" ")) {
			frase = frase.concat("&");
			frase_nova = frase.replace(" &", "");
		} else {
			return frase;
		}
		return frase_nova;
	}

}
