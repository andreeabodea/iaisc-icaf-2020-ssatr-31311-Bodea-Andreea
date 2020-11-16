/* This file was generated by SableCC (http://www.sablecc.org/). */

package giotto.giottoc.node;

import giotto.giottoc.analysis.Analysis;

public final class AHostDeclaration extends PHostDeclaration
{
    private THost _host_;
    private TIdent _hostName_;
    private TAddress _address_;
    private PIP _hostIP_;
    private TColon _colon_;
    private TNumber _hostPort_;
    private PHostPriorities _hostPriorities_;

    public AHostDeclaration()
    {
    }

    public AHostDeclaration(
        THost _host_,
        TIdent _hostName_,
        TAddress _address_,
        PIP _hostIP_,
        TColon _colon_,
        TNumber _hostPort_,
        PHostPriorities _hostPriorities_)
    {
        setHost(_host_);

        setHostName(_hostName_);

        setAddress(_address_);

        setHostIP(_hostIP_);

        setColon(_colon_);

        setHostPort(_hostPort_);

        setHostPriorities(_hostPriorities_);

    }
    public Object clone()
    {
        return new AHostDeclaration(
            (THost) cloneNode(_host_),
            (TIdent) cloneNode(_hostName_),
            (TAddress) cloneNode(_address_),
            (PIP) cloneNode(_hostIP_),
            (TColon) cloneNode(_colon_),
            (TNumber) cloneNode(_hostPort_),
            (PHostPriorities) cloneNode(_hostPriorities_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAHostDeclaration(this);
    }

    public THost getHost()
    {
        return _host_;
    }

    public void setHost(THost node)
    {
        if (_host_ != null)
        {
            _host_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _host_ = node;
    }

    public TIdent getHostName()
    {
        return _hostName_;
    }

    public void setHostName(TIdent node)
    {
        if (_hostName_ != null)
        {
            _hostName_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _hostName_ = node;
    }

    public TAddress getAddress()
    {
        return _address_;
    }

    public void setAddress(TAddress node)
    {
        if (_address_ != null)
        {
            _address_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _address_ = node;
    }

    public PIP getHostIP()
    {
        return _hostIP_;
    }

    public void setHostIP(PIP node)
    {
        if (_hostIP_ != null)
        {
            _hostIP_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _hostIP_ = node;
    }

    public TColon getColon()
    {
        return _colon_;
    }

    public void setColon(TColon node)
    {
        if (_colon_ != null)
        {
            _colon_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _colon_ = node;
    }

    public TNumber getHostPort()
    {
        return _hostPort_;
    }

    public void setHostPort(TNumber node)
    {
        if (_hostPort_ != null)
        {
            _hostPort_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _hostPort_ = node;
    }

    public PHostPriorities getHostPriorities()
    {
        return _hostPriorities_;
    }

    public void setHostPriorities(PHostPriorities node)
    {
        if (_hostPriorities_ != null)
        {
            _hostPriorities_.parent(null);
        }

        if (node != null)
        {
            if (node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _hostPriorities_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_host_)
            + toString(_hostName_)
            + toString(_address_)
            + toString(_hostIP_)
            + toString(_colon_)
            + toString(_hostPort_)
            + toString(_hostPriorities_);
    }

    void removeChild(Node child)
    {
        if (_host_ == child)
        {
            _host_ = null;
            return;
        }

        if (_hostName_ == child)
        {
            _hostName_ = null;
            return;
        }

        if (_address_ == child)
        {
            _address_ = null;
            return;
        }

        if (_hostIP_ == child)
        {
            _hostIP_ = null;
            return;
        }

        if (_colon_ == child)
        {
            _colon_ = null;
            return;
        }

        if (_hostPort_ == child)
        {
            _hostPort_ = null;
            return;
        }

        if (_hostPriorities_ == child)
        {
            _hostPriorities_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if (_host_ == oldChild)
        {
            setHost((THost) newChild);
            return;
        }

        if (_hostName_ == oldChild)
        {
            setHostName((TIdent) newChild);
            return;
        }

        if (_address_ == oldChild)
        {
            setAddress((TAddress) newChild);
            return;
        }

        if (_hostIP_ == oldChild)
        {
            setHostIP((PIP) newChild);
            return;
        }

        if (_colon_ == oldChild)
        {
            setColon((TColon) newChild);
            return;
        }

        if (_hostPort_ == oldChild)
        {
            setHostPort((TNumber) newChild);
            return;
        }

        if (_hostPriorities_ == oldChild)
        {
            setHostPriorities((PHostPriorities) newChild);
            return;
        }

    }
}
