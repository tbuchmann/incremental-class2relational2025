//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:6.0.16
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using NMF.Collections.Generic;
using NMF.Collections.ObjectModel;
using NMF.Expressions;
using NMF.Expressions.Linq;
using NMF.Models;
using NMF.Models.Collections;
using NMF.Models.Expressions;
using NMF.Models.Meta;
using NMF.Models.Repository;
using NMF.Serialization;
using NMF.Utilities;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Diagnostics;
using System.Linq;

namespace HSRM.TTC2023.ClassToRelational.Relational_
{
    
    
    /// <summary>
    /// The default implementation of the Table class
    /// </summary>
    [XmlNamespaceAttribute("Relational")]
    [XmlNamespacePrefixAttribute("relational_")]
    [ModelRepresentationClassAttribute("https://github.com/ATL-Research/incremental-class2relational/Relational.nmeta#//T" +
        "able")]
    [DebuggerDisplayAttribute("Table {Name}")]
    public partial class Table : Named, ITable, IModelElement
    {
        
        private static Lazy <ITypedElement> _colReference = new Lazy <ITypedElement>(RetrieveColReference);
        
        /// <summary>
        /// The backing field for the Col property
        /// </summary>
        [DebuggerBrowsableAttribute(DebuggerBrowsableState.Never)]
        private TableColCollection _col;
        
        private static Lazy <ITypedElement> _keyReference = new Lazy <ITypedElement>(RetrieveKeyReference);
        
        /// <summary>
        /// The backing field for the Key property
        /// </summary>
        [DebuggerBrowsableAttribute(DebuggerBrowsableState.Never)]
        private TableKeyCollection _key;
        
        private static IClass _classInstance;
        
        public Table()
        {
            this._col = new TableColCollection(this);
            this._col.CollectionChanging += this.ColCollectionChanging;
            this._col.CollectionChanged += this.ColCollectionChanged;
            this._key = new TableKeyCollection(this);
            this._key.CollectionChanging += this.KeyCollectionChanging;
            this._key.CollectionChanged += this.KeyCollectionChanged;
        }
        
        /// <summary>
        /// The col property
        /// </summary>
        [DesignerSerializationVisibilityAttribute(DesignerSerializationVisibility.Content)]
        [BrowsableAttribute(false)]
        [XmlElementNameAttribute("col")]
        [XmlAttributeAttribute(false)]
        [ContainmentAttribute()]
        [XmlOppositeAttribute("owner")]
        [ConstantAttribute()]
        public IOrderedSetExpression <IColumn> Col
        {
            get
            {
                return this._col;
            }
        }
        
        /// <summary>
        /// The key property
        /// </summary>
        [DesignerSerializationVisibilityAttribute(DesignerSerializationVisibility.Content)]
        [DisplayNameAttribute("key")]
        [CategoryAttribute("Table")]
        [XmlElementNameAttribute("key")]
        [XmlAttributeAttribute(true)]
        [XmlOppositeAttribute("keyOf")]
        [ConstantAttribute()]
        public ISetExpression <IColumn> Key
        {
            get
            {
                return this._key;
            }
        }
        
        /// <summary>
        /// Gets the child model elements of this model element
        /// </summary>
        public override IEnumerableExpression <IModelElement> Children
        {
            get
            {
                return base.Children.Concat(new TableChildrenCollection(this));
            }
        }
        
        /// <summary>
        /// Gets the referenced model elements of this model element
        /// </summary>
        public override IEnumerableExpression <IModelElement> ReferencedElements
        {
            get
            {
                return base.ReferencedElements.Concat(new TableReferencedElementsCollection(this));
            }
        }
        
        /// <summary>
        /// Gets the Class model for this type
        /// </summary>
        public new static IClass ClassInstance
        {
            get
            {
                if ((_classInstance == null))
                {
                    _classInstance = ((IClass)(MetaRepository.Instance.Resolve("https://github.com/ATL-Research/incremental-class2relational/Relational.nmeta#//T" +
                            "able")));
                }
                return _classInstance;
            }
        }
        
        private static ITypedElement RetrieveColReference()
        {
            return ((ITypedElement)(((ModelElement)(HSRM.TTC2023.ClassToRelational.Relational_.Table.ClassInstance)).Resolve("col")));
        }
        
        /// <summary>
        /// Forwards CollectionChanging notifications for the Col property to the parent model element
        /// </summary>
        /// <param name="sender">The collection that raised the change </param>
        /// <param name="e">The original event data </param>
        private void ColCollectionChanging(object sender, NotifyCollectionChangedEventArgs e)
        {
            this.OnCollectionChanging("Col", e, _colReference);
        }
        
        /// <summary>
        /// Forwards CollectionChanged notifications for the Col property to the parent model element
        /// </summary>
        /// <param name="sender">The collection that raised the change </param>
        /// <param name="e">The original event data </param>
        private void ColCollectionChanged(object sender, NotifyCollectionChangedEventArgs e)
        {
            this.OnCollectionChanged("Col", e, _colReference);
        }
        
        private static ITypedElement RetrieveKeyReference()
        {
            return ((ITypedElement)(((ModelElement)(HSRM.TTC2023.ClassToRelational.Relational_.Table.ClassInstance)).Resolve("key")));
        }
        
        /// <summary>
        /// Forwards CollectionChanging notifications for the Key property to the parent model element
        /// </summary>
        /// <param name="sender">The collection that raised the change </param>
        /// <param name="e">The original event data </param>
        private void KeyCollectionChanging(object sender, NotifyCollectionChangedEventArgs e)
        {
            this.OnCollectionChanging("Key", e, _keyReference);
        }
        
        /// <summary>
        /// Forwards CollectionChanged notifications for the Key property to the parent model element
        /// </summary>
        /// <param name="sender">The collection that raised the change </param>
        /// <param name="e">The original event data </param>
        private void KeyCollectionChanged(object sender, NotifyCollectionChangedEventArgs e)
        {
            this.OnCollectionChanged("Key", e, _keyReference);
        }
        
        /// <summary>
        /// Gets the relative URI fragment for the given child model element
        /// </summary>
        /// <returns>A fragment of the relative URI </returns>
        /// <param name="element">The element that should be looked for </param>
        protected override string GetRelativePathForNonIdentifiedChild(IModelElement element)
        {
            int colIndex = ModelHelper.IndexOfReference(this.Col, element);
            if ((colIndex != -1))
            {
                return ModelHelper.CreatePath("col", colIndex);
            }
            return base.GetRelativePathForNonIdentifiedChild(element);
        }
        
        /// <summary>
        /// Resolves the given URI to a child model element
        /// </summary>
        /// <returns>The model element or null if it could not be found </returns>
        /// <param name="reference">The requested reference name </param>
        /// <param name="index">The index of this reference </param>
        protected override IModelElement GetModelElementForReference(string reference, int index)
        {
            if ((reference == "COL"))
            {
                if ((index < this.Col.Count))
                {
                    return this.Col[index];
                }
                else
                {
                    return null;
                }
            }
            return base.GetModelElementForReference(reference, index);
        }
        
        /// <summary>
        /// Gets the Model element collection for the given feature
        /// </summary>
        /// <returns>A non-generic list of elements </returns>
        /// <param name="feature">The requested feature </param>
        protected override System.Collections.IList GetCollectionForFeature(string feature)
        {
            if ((feature == "COL"))
            {
                return this._col;
            }
            if ((feature == "KEY"))
            {
                return this._key;
            }
            return base.GetCollectionForFeature(feature);
        }
        
        /// <summary>
        /// Gets the property name for the given container
        /// </summary>
        /// <returns>The name of the respective container reference </returns>
        /// <param name="container">The container object </param>
        protected override string GetCompositionName(object container)
        {
            if ((container == this._col))
            {
                return "col";
            }
            return base.GetCompositionName(container);
        }
        
        /// <summary>
        /// Gets the Class for this model element
        /// </summary>
        public override IClass GetClass()
        {
            if ((_classInstance == null))
            {
                _classInstance = ((IClass)(MetaRepository.Instance.Resolve("https://github.com/ATL-Research/incremental-class2relational/Relational.nmeta#//T" +
                        "able")));
            }
            return _classInstance;
        }
        
        /// <summary>
        /// The collection class to to represent the children of the Table class
        /// </summary>
        public class TableChildrenCollection : ReferenceCollection, ICollectionExpression <IModelElement>, ICollection <IModelElement>
        {
            
            private Table _parent;
            
            /// <summary>
            /// Creates a new instance
            /// </summary>
            public TableChildrenCollection(Table parent)
            {
                this._parent = parent;
            }
            
            /// <summary>
            /// Gets the amount of elements contained in this collection
            /// </summary>
            public override int Count
            {
                get
                {
                    int count = 0;
                    count = (count + this._parent.Col.Count);
                    return count;
                }
            }
            
            protected override void AttachCore()
            {
                this._parent.Col.AsNotifiable().CollectionChanged += this.PropagateCollectionChanges;
            }
            
            protected override void DetachCore()
            {
                this._parent.Col.AsNotifiable().CollectionChanged -= this.PropagateCollectionChanges;
            }
            
            /// <summary>
            /// Adds the given element to the collection
            /// </summary>
            /// <param name="item">The item to add </param>
            public override void Add(IModelElement item)
            {
                IColumn colCasted = item.As <IColumn>();
                if ((colCasted != null))
                {
                    this._parent.Col.Add(colCasted);
                }
            }
            
            /// <summary>
            /// Clears the collection and resets all references that implement it.
            /// </summary>
            public override void Clear()
            {
                this._parent.Col.Clear();
            }
            
            /// <summary>
            /// Gets a value indicating whether the given element is contained in the collection
            /// </summary>
            /// <returns>True, if it is contained, otherwise False </returns>
            /// <param name="item">The item that should be looked out for </param>
            public override bool Contains(IModelElement item)
            {
                if (this._parent.Col.Contains(item))
                {
                    return true;
                }
                return false;
            }
            
            /// <summary>
            /// Copies the contents of the collection to the given array starting from the given array index
            /// </summary>
            /// <param name="array">The array in which the elements should be copied </param>
            /// <param name="arrayIndex">The starting index </param>
            public override void CopyTo(IModelElement[] array, int arrayIndex)
            {
                IEnumerator <IModelElement> colEnumerator = this._parent.Col.GetEnumerator();
                try
                {
                    for (
                    ; colEnumerator.MoveNext(); 
                    )
                    {
                        array[arrayIndex] = colEnumerator.Current;
                        arrayIndex = (arrayIndex + 1);
                    }
                }
                finally
                {
                    colEnumerator.Dispose();
                }
            }
            
            /// <summary>
            /// Removes the given item from the collection
            /// </summary>
            /// <returns>True, if the item was removed, otherwise False </returns>
            /// <param name="item">The item that should be removed </param>
            public override bool Remove(IModelElement item)
            {
                IColumn columnItem = item.As <IColumn>();
                if (((columnItem != null) 
                            && this._parent.Col.Remove(columnItem)))
                {
                    return true;
                }
                return false;
            }
            
            /// <summary>
            /// Gets an enumerator that enumerates the collection
            /// </summary>
            /// <returns>A generic enumerator </returns>
            public override IEnumerator <IModelElement> GetEnumerator()
            {
                return Enumerable.Empty <IModelElement>().Concat(this._parent.Col).GetEnumerator();
            }
        }
        
        /// <summary>
        /// The collection class to to represent the children of the Table class
        /// </summary>
        public class TableReferencedElementsCollection : ReferenceCollection, ICollectionExpression <IModelElement>, ICollection <IModelElement>
        {
            
            private Table _parent;
            
            /// <summary>
            /// Creates a new instance
            /// </summary>
            public TableReferencedElementsCollection(Table parent)
            {
                this._parent = parent;
            }
            
            /// <summary>
            /// Gets the amount of elements contained in this collection
            /// </summary>
            public override int Count
            {
                get
                {
                    int count = 0;
                    count = (count + this._parent.Col.Count);
                    count = (count + this._parent.Key.Count);
                    return count;
                }
            }
            
            protected override void AttachCore()
            {
                this._parent.Col.AsNotifiable().CollectionChanged += this.PropagateCollectionChanges;
                this._parent.Key.AsNotifiable().CollectionChanged += this.PropagateCollectionChanges;
            }
            
            protected override void DetachCore()
            {
                this._parent.Col.AsNotifiable().CollectionChanged -= this.PropagateCollectionChanges;
                this._parent.Key.AsNotifiable().CollectionChanged -= this.PropagateCollectionChanges;
            }
            
            /// <summary>
            /// Adds the given element to the collection
            /// </summary>
            /// <param name="item">The item to add </param>
            public override void Add(IModelElement item)
            {
                IColumn colCasted = item.As <IColumn>();
                if ((colCasted != null))
                {
                    this._parent.Col.Add(colCasted);
                }
                IColumn keyCasted = item.As <IColumn>();
                if ((keyCasted != null))
                {
                    this._parent.Key.Add(keyCasted);
                }
            }
            
            /// <summary>
            /// Clears the collection and resets all references that implement it.
            /// </summary>
            public override void Clear()
            {
                this._parent.Col.Clear();
                this._parent.Key.Clear();
            }
            
            /// <summary>
            /// Gets a value indicating whether the given element is contained in the collection
            /// </summary>
            /// <returns>True, if it is contained, otherwise False </returns>
            /// <param name="item">The item that should be looked out for </param>
            public override bool Contains(IModelElement item)
            {
                if (this._parent.Col.Contains(item))
                {
                    return true;
                }
                if (this._parent.Key.Contains(item))
                {
                    return true;
                }
                return false;
            }
            
            /// <summary>
            /// Copies the contents of the collection to the given array starting from the given array index
            /// </summary>
            /// <param name="array">The array in which the elements should be copied </param>
            /// <param name="arrayIndex">The starting index </param>
            public override void CopyTo(IModelElement[] array, int arrayIndex)
            {
                IEnumerator <IModelElement> colEnumerator = this._parent.Col.GetEnumerator();
                try
                {
                    for (
                    ; colEnumerator.MoveNext(); 
                    )
                    {
                        array[arrayIndex] = colEnumerator.Current;
                        arrayIndex = (arrayIndex + 1);
                    }
                }
                finally
                {
                    colEnumerator.Dispose();
                }
                IEnumerator <IModelElement> keyEnumerator = this._parent.Key.GetEnumerator();
                try
                {
                    for (
                    ; keyEnumerator.MoveNext(); 
                    )
                    {
                        array[arrayIndex] = keyEnumerator.Current;
                        arrayIndex = (arrayIndex + 1);
                    }
                }
                finally
                {
                    keyEnumerator.Dispose();
                }
            }
            
            /// <summary>
            /// Removes the given item from the collection
            /// </summary>
            /// <returns>True, if the item was removed, otherwise False </returns>
            /// <param name="item">The item that should be removed </param>
            public override bool Remove(IModelElement item)
            {
                IColumn columnItem = item.As <IColumn>();
                if (((columnItem != null) 
                            && this._parent.Col.Remove(columnItem)))
                {
                    return true;
                }
                if (((columnItem != null) 
                            && this._parent.Key.Remove(columnItem)))
                {
                    return true;
                }
                return false;
            }
            
            /// <summary>
            /// Gets an enumerator that enumerates the collection
            /// </summary>
            /// <returns>A generic enumerator </returns>
            public override IEnumerator <IModelElement> GetEnumerator()
            {
                return Enumerable.Empty <IModelElement>().Concat(this._parent.Col).Concat(this._parent.Key).GetEnumerator();
            }
        }
    }
}

