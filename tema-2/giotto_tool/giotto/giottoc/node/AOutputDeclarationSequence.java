/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class AOutputDeclarationSequence extends POutputDeclarationSequence
{
    private TOutput _output_;
    private final LinkedList _outputDeclarationList_ = new TypedLinkedList(new OutputDeclarationList_Cast());

    public AOutputDeclarationSequence()
    {
    }

    public AOutputDeclarationSequence(
        TOutput _output_,
        List _outputDeclarationList_)
    {
        setOutput(_output_);

        {
            this._outputDeclarationList_.clear();
            this._outputDeclarationList_.addAll(_outputDeclarationList_);
        }

    }

    public AOutputDeclarationSequence(
        TOutput _output_,
        XPOutputDeclaration _outputDeclarationList_)
    {
        setOutput(_output_);

        if (_outputDeclarationList_ != null)
        {
            while (_outputDeclarationList_ instanceof X1POutputDeclaration)
            {
                this._outputDeclarationList_.addFirst(((X1POutputDeclaration) _outputDeclarationList_).getPOutputDeclaration());
                _outputDeclarationList_ = ((X1POutputDeclaration) _outputDeclarationList_).getXPOutputDeclaration();
            }
            this._outputDeclarationList_.addFirst(((X2POutputDeclaration) _outputDeclarationList_).getPOutputDeclaration());
        }

    }
    public Object clone()
    {
        return new AOutputDeclarationSequence(
            (TOutput) cloneNode(_output_),
            cloneList(_outputDeclarationList_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAOutputDeclarationSequence(this);
    }

    public TOutput getOutput()
    {
        return _output_;
    }

    public void setOutput(TOutput node)
    {
        if (_output_ != null)
        {
            _output_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _output_ = node;
    }

    public LinkedList getOutputDeclarationList()
    {
        return _outputDeclarationList_;
    }

    public void setOutputDeclarationList(List list)
    {
        _outputDeclarationList_.clear();
        _outputDeclarationList_.addAll(list);
    }

    public String toString()
    {
        return ""
            + toString(_output_)
            + toString(_outputDeclarationList_);
    }

    void removeChild(Node child)
    {
        if (_output_ == child)
        {
            _output_ = null;
            return;
        }

        if (_outputDeclarationList_.remove(child))
        {
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if (_output_ == oldChild)
        {
            setOutput((TOutput) newChild);
            return;
        }

        for (ListIterator i = _outputDeclarationList_.listIterator(); i.hasNext();)
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

    private class OutputDeclarationList_Cast implements Cast
    {
        public Object cast(Object o)
        {
            POutputDeclaration node = (POutputDeclaration) o;

            if ((node.parent() != null) &&
                (node.parent() != AOutputDeclarationSequence.this))
            {
                node.parent().removeChild(node);
            }

            if ((node.parent() == null) ||
                (node.parent() != AOutputDeclarationSequence.this))
            {
                node.parent(AOutputDeclarationSequence.this);
            }

            return node;
        }
    }
}