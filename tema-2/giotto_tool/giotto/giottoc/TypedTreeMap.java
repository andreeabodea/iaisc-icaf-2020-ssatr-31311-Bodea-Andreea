/*
  Copyright (c) 2002-2004 The Regents of the University of California.
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
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * This file is part of SableCC.                             *
 * See the file "LICENSE" for copyright information and the  *
 * terms and conditions for copying, distribution and        *
 * modification of SableCC.                                  *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package giotto.giottoc;

import giotto.giottoc.node.Cast;
import giotto.giottoc.node.NoCast;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author M.A.A. Sanvido
 * @version TypedTreeMap.java,v 1.8 2004/10/01 01:10:19 cxh Exp
 * @since Giotto 1.0.1
 */
public class TypedTreeMap extends TreeMap
{
    private Cast keyCast;
    private Cast valueCast;
    private Set entries;

    public TypedTreeMap()
    {
        super();

        keyCast = NoCast.instance;
        valueCast = NoCast.instance;
    }

    public TypedTreeMap(Comparator comparator)
    {
        super(comparator);

        keyCast = NoCast.instance;
        valueCast = NoCast.instance;
    }

    public TypedTreeMap(Map map)
    {
        super(map);

        keyCast = NoCast.instance;
        valueCast = NoCast.instance;
    }

    public TypedTreeMap(SortedMap smap)
    {
        super(smap);

        keyCast = NoCast.instance;
        valueCast = NoCast.instance;
    }

    public TypedTreeMap(Cast keyCast, Cast valueCast)
    {
        super();

        this.keyCast = keyCast;
        this.valueCast = valueCast;
    }

    public TypedTreeMap(Comparator comparator, Cast keyCast, Cast valueCast)
    {
        super(comparator);

        this.keyCast = keyCast;
        this.valueCast = valueCast;
    }

    public Object clone()
    {
        return new TypedTreeMap(this, keyCast, valueCast);
    }

    public TypedTreeMap(Map map, Cast keyCast, Cast valueCast)
    {
        super(map);

        this.keyCast = keyCast;
        this.valueCast = valueCast;
    }

    public TypedTreeMap(SortedMap smap, Cast keyCast, Cast valueCast)
    {
        super(smap);

        this.keyCast = keyCast;
        this.valueCast = valueCast;
    }

    public Cast getKeyCast()
    {
        return keyCast;
    }

    public Cast getValueCast()
    {
        return valueCast;
    }

    public Set entrySet()
    {
        if (entries == null)
            {
                entries = new EntrySet(super.entrySet());
            }

        return entries;
    }

    public Object put(Object key, Object value)
    {
        return super.put(keyCast.cast(key), valueCast.cast(value));
    }

    private class EntrySet extends AbstractSet
    {
        private Set set;

        EntrySet(Set set)
        {
            this.set = set;
        }

        public int size()
        {
            return set.size();
        }

        public Iterator iterator()
        {
            return new EntryIterator(set.iterator());
        }
    }

    private class EntryIterator implements Iterator
    {
        private Iterator iterator;

        EntryIterator(Iterator iterator)
        {
            this.iterator = iterator;
        }

        public boolean hasNext()
        {
            return iterator.hasNext();
        }

        public Object next()
        {
            return new TypedEntry((Map.Entry) iterator.next());
        }

        public void remove()
        {
            iterator.remove();
        }
    }

    private class TypedEntry implements Map.Entry
    {
        private Map.Entry entry;

        TypedEntry(Map.Entry entry)
        {
            this.entry = entry;
        }

        public Object getKey()
        {
            return entry.getKey();
        }

        public Object getValue()
        {
            return entry.getValue();
        }

        public Object setValue(Object value)
        {
            return entry.setValue(valueCast.cast(value));
        }
    }
}
