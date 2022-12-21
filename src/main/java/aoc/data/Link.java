package aoc.data;

public class Link<T> {
    private final T value;
    private Link<T> previous;
    private Link<T> next;

    public Link(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public Link<T> getNext() {
        return next;
    }

    public void setNext(Link<T> next) {
        this.next = next;
        next.previous = this;
    }

    public Link<T> getPrevious() {
        return previous;
    }

    public void setPrevious(Link<T> previous) {
        this.previous = previous;
        previous.next = this;
    }

    public void moveForward() {
        final Link<T> temp = next.getNext();
        previous.setNext(next);
        next.setNext(this);
        this.setNext(temp);
    }

    @Deprecated
    //"Does not work as intended!"
    public void moveBackward() {
        final Link<T> temp = previous.getPrevious();
        next.setPrevious(previous);
        previous.setPrevious(this);
        temp.setPrevious(this);
    }
}
