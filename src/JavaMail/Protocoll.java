package JavaMail;


/**
 * Classe responsável por representra a possivel escolha de protocolo para 
 * o envio de e-mail: SMTP, STMPS OU TLS...
 * @author marcelly.paula
 *
 */
public enum Protocoll {
	STMP(1), SMTPS(2), TLS(3);

	public int searchType;

	private Protocoll(int val) {
		searchType = val;
	}

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
}
