/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class AActuatorDeclaration extends PActuatorDeclaration
{
    private TIdent _typeName_;
    private TIdent _portName_;
    private PDeviceDriver _deviceDriver_;
    private PPortMappingAnnotation _portAnnotation_;
    private TSemicolon _semicolon_;

    public AActuatorDeclaration()
    {
    }

    public AActuatorDeclaration(
        TIdent _typeName_,
        TIdent _portName_,
        PDeviceDriver _deviceDriver_,
        PPortMappingAnnotation _portAnnotation_,
        TSemicolon _semicolon_)
    {
        setTypeName(_typeName_);

        setPortName(_portName_);

        setDeviceDriver(_deviceDriver_);

        setPortAnnotation(_portAnnotation_);

        setSemicolon(_semicolon_);

    }
    public Object clone()
    {
        return new AActuatorDeclaration(
            (TIdent) cloneNode(_typeName_),
            (TIdent) cloneNode(_portName_),
            (PDeviceDriver) cloneNode(_deviceDriver_),
            (PPortMappingAnnotation) cloneNode(_portAnnotation_),
            (TSemicolon) cloneNode(_semicolon_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAActuatorDeclaration(this);
    }

    public TIdent getTypeName()
    {
        return _typeName_;
    }

    public void setTypeName(TIdent node)
    {
        if (_typeName_ != null)
        {
            _typeName_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _typeName_ = node;
    }

    public TIdent getPortName()
    {
        return _portName_;
    }

    public void setPortName(TIdent node)
    {
        if (_portName_ != null)
        {
            _portName_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _portName_ = node;
    }

    public PDeviceDriver getDeviceDriver()
    {
        return _deviceDriver_;
    }

    public void setDeviceDriver(PDeviceDriver node)
    {
        if (_deviceDriver_ != null)
        {
            _deviceDriver_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _deviceDriver_ = node;
    }

    public PPortMappingAnnotation getPortAnnotation()
    {
        return _portAnnotation_;
    }

    public void setPortAnnotation(PPortMappingAnnotation node)
    {
        if (_portAnnotation_ != null)
        {
            _portAnnotation_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _portAnnotation_ = node;
    }

    public TSemicolon getSemicolon()
    {
        return _semicolon_;
    }

    public void setSemicolon(TSemicolon node)
    {
        if (_semicolon_ != null)
        {
            _semicolon_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _semicolon_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_typeName_)
            + toString(_portName_)
            + toString(_deviceDriver_)
            + toString(_portAnnotation_)
            + toString(_semicolon_);
    }

    void removeChild(Node child)
    {
        if (_typeName_ == child)
        {
            _typeName_ = null;
            return;
        }

        if (_portName_ == child)
        {
            _portName_ = null;
            return;
        }

        if (_deviceDriver_ == child)
        {
            _deviceDriver_ = null;
            return;
        }

        if (_portAnnotation_ == child)
        {
            _portAnnotation_ = null;
            return;
        }

        if (_semicolon_ == child)
        {
            _semicolon_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if (_typeName_ == oldChild)
        {
            setTypeName((TIdent) newChild);
            return;
        }

        if (_portName_ == oldChild)
        {
            setPortName((TIdent) newChild);
            return;
        }

        if (_deviceDriver_ == oldChild)
        {
            setDeviceDriver((PDeviceDriver) newChild);
            return;
        }

        if (_portAnnotation_ == oldChild)
        {
            setPortAnnotation((PPortMappingAnnotation) newChild);
            return;
        }

        if (_semicolon_ == oldChild)
        {
            setSemicolon((TSemicolon) newChild);
            return;
        }

    }
}
