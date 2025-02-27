package dsa.lab02.exercises;

import dsa.lab02.base.LinkedList;
import dsa.lab02.base.LinkedNode;
import dsa.lib.TODO;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A singly-linked list.
 * <p>
 * Nodes are only directly linked to their successors.
 * Holds references to both the first and last nodes (if non-empty).
 *
 * @param <Item> the item type
 */
public class SinglyLinkedList<Item>
  implements LinkedList<Item>
{
  private int size = 0;
  private Node<Item> first = null;
  private Node<Item> last = null;

  //<editor-fold defaultstate="collapsed" desc="Constructors">

  /**
   * Construct an empty singly-linked list.
   */
  public SinglyLinkedList()
  {
  }

  /**
   * Construct a singly-linked list containing the given items.
   *
   * @param items the items
   */
  public SinglyLinkedList(Iterable<Item> items)
  {
    for (Item item : items)
    {
      this.insertLast(item);
    }
  }

  /**
   * Construct a singly-linked list containing the given items.
   *
   * @param items the items
   */
  @SafeVarargs
  public SinglyLinkedList(Item... items)
  {
    this(Arrays.asList(items));
  }

  //</editor-fold>

  @Override
  public int size()
  {
    return this.size;
  }

  @Override
  public Node<Item> node(int index)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index >= this.size)
    {
      throw new IndexOutOfBoundsException();
    }
    // checks if index == to size of list - 1
    // determines if index is at last point in list
    if (index == this.size - 1)
    {
      // returns last node in list
      return this.last;
    }
    //create a node variable as the first point in list
    Node<Item> node = this.first;
    // for loop from 0 -> index
    //loops thru til at the node of the specified index
    for (int i = 0; i < index; i++)
    {
      node = node.next;
    }
    //returns node at this index
    return node;

  }

  @Override
  public void insert(int index, Item item)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index > this.size)
    {
      throw new IndexOutOfBoundsException();
    }
    //checks if the list is empty
    if (this.isEmpty()){
      // creates a new node with the given item
      // since list is empty set new node to first and last node in list
      this.first = this.last = new Node<>(this,item);
      this.size = 1; // initialises size to 1
    }
    // else if given index is 0
    else if (index == 0){
      //create new node and set it to first item in  the list
      // set the nodes next pointer to current first node
      this.first = new Node<>(this, item, this.first);
      //increment size of list
      this.size++;
    }
    else
    {
      // finds node at position index -1
      //then calls insertnext to insert the new node into the spot after index -1
      this.node(index - 1).insertNext(item);

    }

  }

  @Override
  public Item remove(int index)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index >= this.size)
    {
      throw new IndexOutOfBoundsException();
    }
    if (this.isEmpty())
    {
      throw new IndexOutOfBoundsException();
    }
    //if index = 0
    if (index == 0){
      // if nodes index = 0 then remove first item in list
      return this.first.remove();
    }
    else{
      // finds the node at index -1 and then calls removenext method
      return this.node(index - 1).removeNext();
    }
  }

  /**
   * A node in a singly-linked list.
   * <p>
   * Only holds a direct link to the next node.
   *
   * @param <Item> the item type
   */
  public static class Node<Item>
    implements LinkedNode<Item>
  {
    private SinglyLinkedList<Item> list;
    private Item item;
    private Node<Item> next;

    /**
     * Construct a node with the given item.
     *
     * @param list the containing linked list
     * @param item the contained item
     */
    public Node(SinglyLinkedList<Item> list, Item item)
    {
      this(list, item, null);
    }

    /**
     * Construct a node with the given item and successor.
     *
     * @param list the containing linked list
     * @param item the contained item
     * @param next the next node
     */
    public Node(SinglyLinkedList<Item> list, Item item, Node<Item> next)
    {
      this.list = list;
      this.item = item;
      this.next = next;
    }

    @Override
    public SinglyLinkedList<Item> list()
    {
      return this.list;
    }

    @Override
    public Item item()
    {
      return this.item;
    }

    @Override
    public void setItem(Item item)
    {
      this.item = item;
    }

    @Override
    public Node<Item> previous()
    {
      if (this.isFirst())
      {
        return null;
      }
      Node<Item> node = this.list.first;
      while (node.next != this)
      {
        node = node.next;
      }
      return node;
    }

    @Override
    public Node<Item> next()
    {
      return this.next;
    }

    @Override
    public void insertPrevious(Item item)
    {
      //create a new node object
      Node<Item> node = new Node<Item>(this.list, item, this);
      //if current node is first node, if true set new node as first node
      if(this.isFirst()){
        this.list.first = node;
      }else {//if not first node set next reference
        this.previous().next = node;
      }
      this.list.size++;


    }

    @Override
    public void insertNext(Item item)
    {
      //changes this.next into our new node item we just added
      this.next = new Node<Item>(this.list, item, this.next);
      //checks if this is the last node
      if (this.isLast()){
        //sets last item in the list as the item we just added
        this.list.last = this.next;
      }
      //imcrement list size
      this.list.size++;

    }

    @Override
    public Item remove()
    {
      //if this node is first node
      if (this.isFirst())
      {
        //sets first node to next node
        this.list.first = this.next;
        //decrease list size
        this.list.size--;
        return this.item;
      }
      return this.previous().removeNext();
    }

    @Override
    public Item removePrevious()
      throws NoSuchElementException
    {
      if (this.isFirst())
      {
        throw new NoSuchElementException();
      }
      Node<Item> node = this.list.first;
      if (node.next == this)
      {
        this.list.first = this;
        this.list.size--;
        return node.item;
      }
      while (node.next.next != this)
      {
        node = node.next;
      }
      return node.removeNext();
    }

    @Override
    public Item removeNext()
      throws NoSuchElementException
    {
      if (this.isLast())
      {
        throw new NoSuchElementException();
      }
      //checks if next node is the last node in the list
      if(this.next.isLast()){
        //if next node is last in list update last reference to current node(this)
        this.list.last = this;
      }
      //retrieve next item and assigns it to variable deleted
      Item deleted = this.next.item;
      //updates next pointer reference to the node after next pointer
      this.next = this.next.next;
      //returns removed value
      this.list.size--;
      return deleted;
    }
  }

  //<editor-fold defaultstate="collapsed" desc="Iteration">

  // NOTE: SinglyLinkedList.reversed() will use the LinkedList.reversed()
  //       implementation which starts from the last node and repeatedly calls
  //       previous(), which for singly-linked lists is O(n), making it O(n^2)
  //       overall. We don't implement it here, but FYI it's possible to
  //       implement O(n) reverse-iteration over singly-linked lists if you
  //       first create a reversed copy of the list by doing essentially:
  //         SinglyLinkedList<Item> copy = new SinglyLinkedList();
  //         for (Item item : list)
  //         {
  //           copy.insertFirst(item);
  //         }
  //       which is O(n), and then simply iterating over that reversed copy,
  //       which is also O(n). This has two disadvantages, the first being that
  //       it requires O(n) additional storage for the copy, and the second
  //       being that you're iterating over a copy rather than the list itself,
  //       though that doesn't matter if you're not making any changes to the
  //       list or copy during the iteration. It also only really works for
  //       reversed(), not reversedNodes(), as the nodes yielded would be
  //       different nodes in the temporary copy rather than in the actual list.

  //</editor-fold>
}
