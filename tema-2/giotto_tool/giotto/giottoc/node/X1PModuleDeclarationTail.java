/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;


public final class X1PModuleDeclarationTail extends XPModuleDeclarationTail
{
    private XPModuleDeclarationTail _xPModuleDeclarationTail_;
    private PModuleDeclarationTail _pModuleDeclarationTail_;

    public X1PModuleDeclarationTail()
    {
    }

    public X1PModuleDeclarationTail(
        XPModuleDeclarationTail _xPModuleDeclarationTail_,
        PModuleDeclarationTail _pModuleDeclarationTail_)
    {
        setXPModuleDeclarationTail(_xPModuleDeclarationTail_);
        setPModuleDeclarationTail(_pModuleDeclarationTail_);
    }

    public Object clone()
    {
        throw new RuntimeException("Unsupported Operation");
    }

    public void apply(Switch sw)
    {
        throw new RuntimeException("Switch not supported.");
    }

    public XPModuleDeclarationTail getXPModuleDeclarationTail()
    {
        return _xPModuleDeclarationTail_;
    }

    public void setXPModuleDeclarationTail(XPModuleDeclarationTail node)
    {
        if (_xPModuleDeclarationTail_ != null)
        {
            _xPModuleDeclarationTail_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _xPModuleDeclarationTail_ = node;
    }

    public PModuleDeclarationTail getPModuleDeclarationTail()
    {
        return _pModuleDeclarationTail_;
    }

    public void setPModuleDeclarationTail(PModuleDeclarationTail node)
    {
        if (_pModuleDeclarationTail_ != null)
        {
            _pModuleDeclarationTail_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _pModuleDeclarationTail_ = node;
    }

    void removeChild(Node child)
    {
        if (_xPModuleDeclarationTail_ == child)
        {
            _xPModuleDeclarationTail_ = null;
        }

        if (_pModuleDeclarationTail_ == child)
        {
            _pModuleDeclarationTail_ = null;
        }
    }

    void replaceChild(Node oldChild, Node newChild)
    {
    }

    public String toString()
    {
        return "" +
            toString(_xPModuleDeclarationTail_) +
            toString(_pModuleDeclarationTail_);
    }
}
