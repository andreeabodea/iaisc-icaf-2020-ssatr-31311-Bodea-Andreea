/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;


public final class X2PTaskDeclaration extends XPTaskDeclaration
{
    private PTaskDeclaration _pTaskDeclaration_;

    public X2PTaskDeclaration()
    {
    }

    public X2PTaskDeclaration(
        PTaskDeclaration _pTaskDeclaration_)
    {
        setPTaskDeclaration(_pTaskDeclaration_);
    }

    public Object clone()
    {
        throw new RuntimeException("Unsupported Operation");
    }

    public void apply(Switch sw)
    {
        throw new RuntimeException("Switch not supported.");
    }

    public PTaskDeclaration getPTaskDeclaration()
    {
        return _pTaskDeclaration_;
    }

    public void setPTaskDeclaration(PTaskDeclaration node)
    {
        if (_pTaskDeclaration_ != null)
        {
            _pTaskDeclaration_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _pTaskDeclaration_ = node;
    }

    void removeChild(Node child)
    {
        if (_pTaskDeclaration_ == child)
        {
            _pTaskDeclaration_ = null;
        }
    }

    void replaceChild(Node oldChild, Node newChild)
    {
    }

    public String toString()
    {
        return "" +
            toString(_pTaskDeclaration_);
    }
}
