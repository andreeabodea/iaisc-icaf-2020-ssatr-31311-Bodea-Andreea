/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class AActualScopedParameter extends PActualScopedParameter
{
    private TIdent _parameterName_;

    public AActualScopedParameter()
    {
    }

    public AActualScopedParameter(
        TIdent _parameterName_)
    {
        setParameterName(_parameterName_);

    }
    public Object clone()
    {
        return new AActualScopedParameter(
            (TIdent) cloneNode(_parameterName_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAActualScopedParameter(this);
    }

    public TIdent getParameterName()
    {
        return _parameterName_;
    }

    public void setParameterName(TIdent node)
    {
        if (_parameterName_ != null)
        {
            _parameterName_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _parameterName_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_parameterName_);
    }

    void removeChild(Node child)
    {
        if (_parameterName_ == child)
        {
            _parameterName_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if (_parameterName_ == oldChild)
        {
            setParameterName((TIdent) newChild);
            return;
        }

    }
}
