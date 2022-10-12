// This is a custom exception if the sequence in arrayDS is empty

public class EmptySequenceException extends RuntimeException {

	private static final long serialVersionUID = 575262834263843711L;

	public EmptySequenceException(String reason) {
		super(reason);
	}
}
