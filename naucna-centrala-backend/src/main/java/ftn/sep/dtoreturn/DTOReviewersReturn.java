package ftn.sep.dtoreturn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ftn.sep.model.PotentialReviewers;
import ftn.sep.model.User;

public class DTOReviewersReturn {
	
	private Set<DTOReviewerReturn> all;
	private Set<DTOReviewerReturn> area;
	private Set<DTOReviewerReturn> similar;
	private Set<DTOReviewerReturn> differentRegion;
	private int numberOfReviewers;
	private List<String> choosenReviewers;
	
	public DTOReviewersReturn() {
		this.all = new HashSet<>();
		this.area = new HashSet<>();
		this.similar = new HashSet<>();
		this.differentRegion = new HashSet<>();
		this.choosenReviewers = new ArrayList<>();
	}
	
	public DTOReviewersReturn(Set<DTOReviewerReturn> all, Set<DTOReviewerReturn> area, Set<DTOReviewerReturn> similar,
			Set<DTOReviewerReturn> differentRegion, int numberOfReviewers, List<String> choosenReviewers) {
		super();
		this.all = all;
		this.area = area;
		this.similar = similar;
		this.differentRegion = differentRegion;
		this.numberOfReviewers = numberOfReviewers;
		this.choosenReviewers = choosenReviewers;
	}
	
	public static DTOReviewersReturn convert(PotentialReviewers pr, int numberOfReviewers, List<String> choosen) {
		DTOReviewersReturn dtoRR = new DTOReviewersReturn();
		dtoRR.setNumberOfReviewers(numberOfReviewers);
		dtoRR.setChoosenReviewers(choosen);
		dtoRR.setAll(DTOReviewersReturn.convertSet(pr.getAllReviewers()));
		dtoRR.setArea(DTOReviewersReturn.convertSet(pr.getAreaReviewers()));
		dtoRR.setSimilar(DTOReviewersReturn.convertSet(pr.getSimilarArticleReviewers()));
		dtoRR.setDifferentRegion(DTOReviewersReturn.convertSet(pr.getDifferentRegionReviwers()));
		
		return dtoRR;
	}
	
	public static Set<DTOReviewerReturn> convertSet(Set<User> set) {
		Set<DTOReviewerReturn> reviewers = new HashSet<>();
		for (User user : set) {
			DTOReviewerReturn dtoRR = DTOReviewerReturn.convert(user);
			reviewers.add(dtoRR);
		}
		
		return reviewers;
	}

	public Set<DTOReviewerReturn> getAll() {
		return all;
	}

	public void setAll(Set<DTOReviewerReturn> all) {
		this.all = all;
	}

	public Set<DTOReviewerReturn> getArea() {
		return area;
	}

	public void setArea(Set<DTOReviewerReturn> area) {
		this.area = area;
	}

	public Set<DTOReviewerReturn> getSimilar() {
		return similar;
	}

	public void setSimilar(Set<DTOReviewerReturn> similar) {
		this.similar = similar;
	}

	public Set<DTOReviewerReturn> getDifferentRegion() {
		return differentRegion;
	}

	public void setDifferentRegion(Set<DTOReviewerReturn> differentRegion) {
		this.differentRegion = differentRegion;
	}

	public int getNumberOfReviewers() {
		return numberOfReviewers;
	}

	public void setNumberOfReviewers(int numberOfReviewers) {
		this.numberOfReviewers = numberOfReviewers;
	}

	public List<String> getChoosenReviewers() {
		return choosenReviewers;
	}

	public void setChoosenReviewers(List<String> choosenReviewers) {
		this.choosenReviewers = choosenReviewers;
	}

}
