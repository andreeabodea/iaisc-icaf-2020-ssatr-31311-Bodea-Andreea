/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class AConcreteHostList extends PHostList
{
    private PHostNameIdent _hostNameIdent_;
    private final LinkedList _hostTail_ = new TypedLinkedList(new HostTail_Cast());

    public AConcreteHostList()
    {
    }

    public AConcreteHostList(
        PHostNameIdent _hostNameIdent_,
        List _hostTail_)
    {
        setHostNameIdent(_hostNameIdent_);

        {
            this._hostTail_.clear();
            this._hostTail_.addAll(_hostTail_);
        }

    }

    public AConcreteHostList(
        PHostNameIdent _hostNameIdent_,
        XPHostTail _hostTail_)
    {
        setHostNameIdent(_hostNameIdent_);

        if (_hostTail_ != null)
        {
            while (_hostTail_ instanceof X1PHostTail)
            {
                this._hostTail_.addFirst(((X1PHostTail) _hostTail_).getPHostTail());
                _hostTail_ = ((X1PHostTail) _hostTail_).getXPHostTail();
            }
            this._hostTail_.addFirst(((X2PHostTail) _hostTail_).getPHostTail());
        }

    }
    public Object clone()
    {
        return new AConcreteHostList(
            (PHostNameIdent) cloneNode(_hostNameIdent_),
            cloneList(_hostTail_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAConcreteHostList(this);
    }

    public PHostNameIdent getHostNameIdent()
    {
        return _hostNameIdent_;
    }

    public void setHostNameIdent(PHostNameIdent node)
    {
        if (_hostNameIdent_ != null)
        {
            _hostNameIdent_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _hostNameIdent_ = node;
    }

    public LinkedList getHostTail()
    {
        return _hostTail_;
    }

    public void setHostTail(List list)
    {
        _hostTail_.clear();
        _hostTail_.addAll(list);
    }

    public String toString()
    {
        return ""
            + toString(_hostNameIdent_)
            + toString(_hostTail_);
    }

    void removeChild(Node child)
    {
        if (_hostNameIdent_ == child)
        {
            _hostNameIdent_ = null;
            return;
        }

        if (_hostTail_.remove(child))
        {
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if (_hostNameIdent_ == oldChild)
        {
            setHostNameIdent((PHostNameIdent) newChild);
            return;
        }

        for (ListIterator i = _hostTail_.listIterator(); i.hasNext();)
        {
            if (i.next() == oldChild)
            {
                if (newChild != null)
                {
                    i.set(newChild);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

    }

    private class HostTail_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PHostTail node = (PHostTail) o;

            if ((node.parent() != null) &&
                (node.parent() != AConcreteHostList.this))
            {
                node.parent().removeChild(node);
            }

            if ((node.parent() == null) ||
                (node.parent() != AConcreteHostList.this))
            {
                node.parent(AConcreteHostList.this);
            }

            return node;
        }
    }
}
