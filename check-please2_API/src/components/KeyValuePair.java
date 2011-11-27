/** @author Ryan Gaffney */

package components;

public class KeyValuePair
{
    private final String key;
    private final Object value;

    public KeyValuePair(String left, Object right)
    {
        this.key = left;
        this.value = right;
    }

    public String key() { return key; }
    public Object value() { return value; }

    @Override
    public int hashCode() { return key.hashCode() ^ value.hashCode(); }

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        
        if (!(o instanceof KeyValuePair))
            return false;
        
        KeyValuePair pair = (KeyValuePair) o;
        
        return this.key.equals(pair.key()) && this.value.equals(pair.value());
    }
}
