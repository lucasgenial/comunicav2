package br.com.cicom.comunicacicom.DSPrimary.POJO;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public class Aviso {
	private static Aviso aviso = new Aviso();
	private String mensagemCorreto;
	private String linkDeRetornoCorreto;
	private String mensagemErrado;
	private String linkDeRetornoErrado;
	private Boolean ok;
	
	public String mensagem;
	public String link;
	
	public static AvisoBuilderMensagemCorreto criaAviso() {
		return new AvisoBuilderMensagemCorreto();
	}
	
	public static class AvisoBuilderMensagemCorreto {
		
		public AvisoBuilderMensagemErrada mensagemCasoDeuCerto(String mensagem) {
			aviso.mensagemCorreto = mensagem;
			 return new AvisoBuilderMensagemErrada();
		}
	
	}
	
	public static class AvisoBuilderMensagemErrada {
			
			public AvisoBuilderLinkCorreto mensagemCasoDeuErrado(String mensagem) {
				aviso.mensagemErrado = mensagem;
				return new AvisoBuilderLinkCorreto();
			}
		
			
			
			public static class AvisoBuilderLinkCorreto {
				
				public AvisoBuilderLinkErrado linkCasoDeuCerto(String mensagem) {
					aviso.linkDeRetornoCorreto = mensagem;
					return new AvisoBuilderLinkErrado();
				}
			}
			
			public static class AvisoBuilderLinkErrado {
				
				public AconteceuCorretoOuNao linkCasoDeuErrado(String mensagem) {
					 aviso.linkDeRetornoErrado = mensagem;
					 return new AconteceuCorretoOuNao();
				}
			}
			
			public static class AconteceuCorretoOuNao{
				
				public FinalizaMensagem booleanoQueVerificaSeDeuCerto(Boolean ok) {
					aviso.ok = ok;
					 return new FinalizaMensagem();
				}
			
			}

			public static class FinalizaMensagem{
				
				public void finalizaMensagem(ModelAndView modelAndView) {
				
					if(aviso.ok != null) {
						if(aviso.ok) {
							aviso.mensagem = aviso.mensagemCorreto;
							aviso.link = aviso.linkDeRetornoCorreto;
						}else {
							aviso.mensagem = aviso.mensagemErrado;
							aviso.link = aviso.linkDeRetornoErrado;
						}
						
						modelAndView.addObject("mensagem",aviso);
					
					}
					
				}
				
				public void finalizaMensagem(Model model) {
					if(aviso.ok != null) {
						if(aviso.ok) {
							aviso.mensagem = aviso.mensagemCorreto;
							aviso.link = aviso.linkDeRetornoCorreto;
						}else {
							aviso.mensagem = aviso.mensagemErrado;
							aviso.link = aviso.linkDeRetornoErrado;
						}			
						model.addAttribute("mensagem",aviso);
						
					}
				}	
			}

			
		}

	public String getMensagem() {
		return mensagem;
	}

	public String getLink() {
		return link;
	}
	
}
