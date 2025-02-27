package dsa.lab02.exercises;

import dsa.lab02.base.DynamicSequence;
import dsa.lib.TODO;

import java.util.Arrays;

/**
 * A static array.
 * <p>
 * A dynamic sequence implemented using a full array
 * (i.e. with as many items as slots).
 * <p>
 * Dynamic operations always reallocate a new array and are all O({@code n})
 * (where {@code n} is the size).
 *
 * @param <Item> the item type
 */
public class StaticArray<Item>
  implements DynamicSequence<Item>
{
  private Item[] items;

  //<editor-fold defaultstate="collapsed" desc="Constructors">

  /**
   * Construct an empty static array.
   */
  @SuppressWarnings("unchecked")
  public StaticArray()
  {
    this.items = (Item[]) new Object[0];
  }

  /**
   * Construct a static array containing the given items.
   *
   * @param items the items
   */
  public StaticArray(Iterable<Item> items)
  {
    this();
    for (Item item : items)
    {
      this.insertLast(item);
    }
  }

  /**
   * Construct a static array containing the given items
   * more efficiently than {@link #StaticArray(Iterable)}.
   *
   * @param items the items
   * @param size  the number of items
   * @throws IllegalArgumentException if {@code size} != {@code n}
   *                                  (where {@code n} is {@code items}'s size)
   */
  @SuppressWarnings("unchecked")
  public StaticArray(Iterable<Item> items, int size)
    throws IllegalArgumentException
  {
    if (size < 0)
    {
      throw new IllegalArgumentException();
    }
    this.items = (Item[]) new Object[size];
    int index = 0;
    for (Item item : items)
    {
      this.items[index++] = item;
    }
    if (index != size)
    {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Construct a static array containing the given items.
   *
   * @param items the items
   */
  @SafeVarargs
  public StaticArray(Item... items)
  {
    this(Arrays.asList(items), items.length);
  }

  //</editor-fold>

  @Override
  public int size()
  {
    return this.items.length;
  }

  @Override
  public Item get(int index)
    throws IndexOutOfBoundsException
  {
    return this.items[index];
  }

  @Override
  public void set(int index, Item item)
    throws IndexOutOfBoundsException
  {
    this.items[index] = item;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void insert(int index, Item item)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index > this.size())
    {
      throw new IndexOutOfBoundsException();
    }
    //create a new array
    Item[] newArray = (Item[]) new Object[this.items.length + 1];
    // loops thru array
    for (int i = 0, j = 0; i < newArray.length; i++)
    {
      if (i == index){// if i = index replace that index w item
        newArray[i] = item;
      }else {
        newArray[i] = this.items[j]; // else add the jth item from the old array into the new array and increment
        j++;
      }
    }
    //replace old array w new array
    this.items = newArray;


  }

  @Override
  @SuppressWarnings("unchecked")
  public Item remove(int index)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index >= this.size())
    {
      throw new IndexOutOfBoundsException();
    }
    Item removedItem = this.items[index];
    // copy to new array (length.items-1) except for item at index
    Item[] newArray = (Item[]) new Object[this.size()- 1];
    // before item we want to remove
    for (int i = 0; i < index; i++)
    {
        newArray[i] = this.items[i];
    }
    for (int i = index; i < newArray.length; i++){
      newArray[i] = this.items[i+1];
    }


    this.items = newArray;
    return removedItem;

  }

}
