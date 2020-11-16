/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class AConcretePriorityList extends PPriorityList
{
    private PPriorityProd _priorityProd_;
    private final LinkedList _priorityTail_ = new TypedLinkedList(new PriorityTail_Cast());

    public AConcretePriorityList()
    {
    }

    public AConcretePriorityList(
        PPriorityProd _priorityProd_,
        List _priorityTail_)
    {
        setPriorityProd(_priorityProd_);

        {
            this._priorityTail_.clear();
            this._priorityTail_.addAll(_priorityTail_);
        }

    }

    public AConcretePriorityList(
        PPriorityProd _priorityProd_,
        XPPriorityTail _priorityTail_)
    {
        setPriorityProd(_priorityProd_);

        if (_priorityTail_ != null)
        {
            while (_priorityTail_ instanceof X1PPriorityTail)
            {
                this._priorityTail_.addFirst(((X1PPriorityTail) _priorityTail_).getPPriorityTail());
                _priorityTail_ = ((X1PPriorityTail) _priorityTail_).getXPPriorityTail();
            }
            this._priorityTail_.addFirst(((X2PPriorityTail) _priorityTail_).getPPriorityTail());
        }

    }
    public Object clone()
    {
        return new AConcretePriorityList(
            (PPriorityProd) cloneNode(_priorityProd_),
            cloneList(_priorityTail_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAConcretePriorityList(this);
    }

    public PPriorityProd getPriorityProd()
    {
        return _priorityProd_;
    }

    public void setPriorityProd(PPriorityProd node)
    {
        if (_priorityProd_ != null)
        {
            _priorityProd_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _priorityProd_ = node;
    }

    public LinkedList getPriorityTail()
    {
        return _priorityTail_;
    }

    public void setPriorityTail(List list)
    {
        _priorityTail_.clear();
        _priorityTail_.addAll(list);
    }

    public String toString()
    {
        return ""
            + toString(_priorityProd_)
            + toString(_priorityTail_);
    }

    void removeChild(Node child)
    {
        if (_priorityProd_ == child)
        {
            _priorityProd_ = null;
            return;
        }

        if (_priorityTail_.remove(child))
        {
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if (_priorityProd_ == oldChild)
        {
            setPriorityProd((PPriorityProd) newChild);
            return;
        }

        for (ListIterator i = _priorityTail_.listIterator(); i.hasNext();)
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

    private class PriorityTail_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PPriorityTail node = (PPriorityTail) o;

            if ((node.parent() != null) &&
                (node.parent() != AConcretePriorityList.this))
            {
                node.parent().removeChild(node);
            }

            if ((node.parent() == null) ||
                (node.parent() != AConcretePriorityList.this))
            {
                node.parent(AConcretePriorityList.this);
            }

            return node;
        }
    }
}
