package ftn.sep.dto;

public class DTOChooseJournal {
	
	private Long journalId;
	
	public DTOChooseJournal() {
	}

	public DTOChooseJournal(Long journalId) {
		super();
		this.journalId = journalId;
	}

	public Long getJournalId() {
		return journalId;
	}

	public void setJournalId(Long journalId) {
		this.journalId = journalId;
	}

}
