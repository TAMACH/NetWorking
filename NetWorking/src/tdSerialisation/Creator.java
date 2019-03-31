package tdSerialisation;

public interface Creator<T extends MySerialisable> {

    public T init();
}
