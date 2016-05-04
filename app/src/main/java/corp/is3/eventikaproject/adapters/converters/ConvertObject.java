package corp.is3.eventikaproject.adapters.converters;

public interface ConvertObject<B, E> {

    public E convert(B object);
}
