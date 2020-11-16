/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class AConcreteActualPortList extends PActualPortList
{
    private PActualPort _actualPort_;
    private final LinkedList _actualPortTail_ = new TypedLinkedList(new ActualPortTail_Cast());

    public AConcreteActualPortList()
    {
    }

    public AConcreteActualPortList(
        PActualPort _actualPort_,
        List _actualPortTail_)
    {
        setActualPort(_actualPort_);

        {
            this._actualPortTail_.clear();
            this._actualPortTail_.addAll(_actualPortTail_);
        }

    }

    public AConcreteActualPortList(
        PActualPort _actualPort_,
        XPActualPortTail _actualPortTail_)
    {
        setActualPort(_actualPort_);

        if (_actualPortTail_ != null)
        {
            while (_actualPortTail_ instanceof X1PActualPortTail)
            {
                this._actualPortTail_.addFirst(((X1PActualPortTail) _actualPortTail_).getPActualPortTail());
                _actualPortTail_ = ((X1PActualPortTail) _actualPortTail_).getXPActualPortTail();
            }
            this._actualPortTail_.addFirst(((X2PActualPortTail) _actualPortTail_).getPActualPortTail());
        }

    }
    public Object clone()
    {
        return new AConcreteActualPortList(
            (PActualPort) cloneNode(_actualPort_),
            cloneList(_actualPortTail_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAConcreteActualPortList(this);
    }

    public PActualPort getActualPort()
    {
        return _actualPort_;
    }

    public void setActualPort(PActualPort node)
    {
        if (_actualPort_ != null)
        {
            _actualPort_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _actualPort_ = node;
    }

    public LinkedList getActualPortTail()
    {
        return _actualPortTail_;
    }

    public void setActualPortTail(List list)
    {
        _actualPortTail_.clear();
        _actualPortTail_.addAll(list);
    }

    public String toString()
    {
        return ""
            + toString(_actualPort_)
            + toString(_actualPortTail_);
    }

    void removeChild(Node child)
    {
        if (_actualPort_ == child)
        {
            _actualPort_ = null;
            return;
        }

        if (_actualPortTail_.remove(child))
        {
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if (_actualPort_ == oldChild)
        {
            setActualPort((PActualPort) newChild);
            return;
        }

        for (ListIterator i = _actualPortTail_.listIterator(); i.hasNext();)
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

    private class ActualPortTail_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PActualPortTail node = (PActualPortTail) o;

            if ((node.parent() != null) &&
                (node.parent() != AConcreteActualPortList.this))
            {
                node.parent().removeChild(node);
            }

            if ((node.parent() == null) ||
                (node.parent() != AConcreteActualPortList.this))
            {
                node.parent(AConcreteActualPortList.this);
            }

            return node;
        }
    }
}