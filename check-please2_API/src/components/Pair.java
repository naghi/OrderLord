/** @author Ryan Gaffney */

package components;

public class Pair<L, R>
{
    private final L left;
    private final R right;

    public Pair(L left, R right)
    {
        this.left = left;
        this.right = right;
    }

    public L left() { return left; }
    public R right() { return right; }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        
        if (!(o instanceof Pair<?, ?>))
            return false;
        
        Pair<?, ?> pair = (Pair<?, ?>) o;
        
        return this.left.equals(pair.left()) && this.right.equals(pair.right());
    }
}
