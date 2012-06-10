package models;

/**
 * Created by IntelliJ IDEA.
 * User: akast
 * Date: 2/27/12
 * Time: 12:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MementoBridge<T> {
    public String toMemento(T t);
    public T fromMemento(String s);
}
