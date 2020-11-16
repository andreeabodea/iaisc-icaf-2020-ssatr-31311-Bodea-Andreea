/*

Copyright (c) 2001-2004 The Regents of the University of California.
All rights reserved.

Permission is hereby granted, without written agreement and without
license or royalty fees, to use, copy, modify, and distribute this
software and its documentation for any purpose, provided that the above
copyright notice and the following two paragraphs appear in all copies
of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
ENHANCEMENTS, OR MODIFICATIONS.

GIOTTO_COPYRIGHT_VERSION_2
COPYRIGHTENDKEY
*/

package giotto.giottoc;

import giotto.giottoc.analysis.DepthFirstAdapter;
import giotto.giottoc.node.AActualPortList;
import giotto.giottoc.node.AActualPortTail;
import giotto.giottoc.node.AActualScopedParameterList;
import giotto.giottoc.node.AActualScopedParameterTail;
import giotto.giottoc.node.AConcreteActualPortList;
import giotto.giottoc.node.AConcreteActualScopedParameterList;
import giotto.giottoc.node.AConcreteFormalPortList;
import giotto.giottoc.node.AConcreteModuleDeclarationList;
import giotto.giottoc.node.AConcreteQualident;
import giotto.giottoc.node.AConcreteScheduleConnectionList;
import giotto.giottoc.node.AConcreteStatePortList;
import giotto.giottoc.node.AFormalPortList;
import giotto.giottoc.node.AFormalPortTail;
import giotto.giottoc.node.AModuleDeclarationList;
import giotto.giottoc.node.AModuleDeclarationTail;
import giotto.giottoc.node.AQualident;
import giotto.giottoc.node.AQualidentTail;
import giotto.giottoc.node.AScheduleConnectionList;
import giotto.giottoc.node.AScheduleConnectionTail;
import giotto.giottoc.node.AStatePortList;
import giotto.giottoc.node.AStatePortTail;

import java.util.LinkedList;
import java.util.ListIterator;

/**

This class implements a tree walker that converts the concrete syntax
tree into an abstract syntax tree.

@author Christoph Kirsch
@version CST2AST.java,v 1.9 2004/09/29 03:51:52 cxh Exp
@since Giotto 1.0.1
*/
public class CST2AST extends DepthFirstAdapter {

    public void outAConcreteModuleDeclarationList(AConcreteModuleDeclarationList node)
    {
        final LinkedList moduleDeclarationList = new LinkedList();

        moduleDeclarationList.add(node.getModuleDeclaration());

        for (ListIterator iterator = node.getModuleDeclarationTail().listIterator(); iterator.hasNext(); )
            {
                final AModuleDeclarationTail moduleDeclarationTail = (AModuleDeclarationTail) iterator.next();

                moduleDeclarationList.add(moduleDeclarationTail.getModuleDeclaration());
            }

        node.replaceBy(new AModuleDeclarationList(moduleDeclarationList));
    }

    public void outAConcreteQualident(AConcreteQualident node)
    {
        final LinkedList ident = new LinkedList();

        ident.add(node.getIdent());

        for (ListIterator iterator = node.getQualidentTail().listIterator(); iterator.hasNext(); )
            {
                final AQualidentTail qualidentTail = (AQualidentTail) iterator.next();

                ident.add(qualidentTail.getIdent());
            }

        node.replaceBy(new AQualident(ident));
    }

    public void outAConcreteFormalPortList(AConcreteFormalPortList node)
    {
        final LinkedList formalPortList = new LinkedList();

        formalPortList.add(node.getFormalPort());

        for (ListIterator iterator = node.getFormalPortTail().listIterator(); iterator.hasNext(); )
            {
                final AFormalPortTail formalPortTail = (AFormalPortTail) iterator.next();

                formalPortList.add(formalPortTail.getFormalPort());
            }

        node.replaceBy(new AFormalPortList(formalPortList));
    }

    public void outAConcreteActualPortList (AConcreteActualPortList node)
    {
        final LinkedList actualPortList = new LinkedList();

        actualPortList.add(node.getActualPort());

        for (ListIterator iterator = node.getActualPortTail().listIterator(); iterator.hasNext(); )
            {
                final AActualPortTail actualPortTail = (AActualPortTail) iterator.next();

                actualPortList.add (actualPortTail.getActualPort());
            }

        node.replaceBy(new AActualPortList(actualPortList));
    }

    public void outAConcreteStatePortList(AConcreteStatePortList node)
    {
        final LinkedList statePortList = new LinkedList();

        statePortList.add(node.getStatePort());

        for (ListIterator iterator = node.getStatePortTail().listIterator(); iterator.hasNext(); )
            {
                final AStatePortTail statePortTail = (AStatePortTail) iterator.next();

                statePortList.add(statePortTail.getStatePort());
            }

        node.replaceBy(new AStatePortList(statePortList));
    }

    public void outAConcreteActualScopedParameterList (AConcreteActualScopedParameterList node)
    {
        final LinkedList actualScopedParameterList = new LinkedList();

        actualScopedParameterList.add(node.getActualScopedParameter());

        for (ListIterator iterator = node.getActualScopedParameterTail().listIterator(); iterator.hasNext(); )
            {
                final AActualScopedParameterTail actualScopedParameterTail = (AActualScopedParameterTail) iterator.next();

                actualScopedParameterList.add (actualScopedParameterTail.getActualScopedParameter());
            }

        node.replaceBy(new AActualScopedParameterList(actualScopedParameterList));
    }

    public void outAConcreteScheduleConnectionList (AConcreteScheduleConnectionList node)
    {
        final LinkedList scheduleConnectionList = new LinkedList();

        scheduleConnectionList.add(node.getScheduleConnection());

        for (ListIterator iterator = node.getScheduleConnectionTail().listIterator(); iterator.hasNext(); )
            {
                final AScheduleConnectionTail scheduleConnectionTail = (AScheduleConnectionTail) iterator.next();

                scheduleConnectionList.add (scheduleConnectionTail.getScheduleConnection());
            }

        node.replaceBy(new AScheduleConnectionList(scheduleConnectionList));
    }
}

