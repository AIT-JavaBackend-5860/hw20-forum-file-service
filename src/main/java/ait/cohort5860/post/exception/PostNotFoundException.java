package ait.cohort5860.post.exception;

/**
 * Исключение выбрасывается, когда пост не найден.
 */
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException() {
        super("Post not found");
    }
}
