package dsa.lab04.exercises;

import dsa.lab02.base.StaticSequence;
import dsa.lab02.exercises.StaticArray;
import dsa.lab04.base.Sorter;
import dsa.lib.TODO;

import java.util.Comparator;

/**
 * Merge sort.
 */
public class MergeSorter
  implements Sorter
{
  @Override
  public <Item> void sort(
    StaticSequence<Item> items,
    Comparator<Item> comparator)
  {

    // split into 2 arrays, array L and array R
    int size = items.size();

    //return when arrays are of size 1
    if (size <= 1)
    {
      return;
    }

    int sizeL = size/2;
    int sizeR = size - sizeL;
    StaticArray<Item> itemsL  = new StaticArray<>((Item[]) new Object[sizeL]);
    StaticArray<Item> itemsR  = new StaticArray<>((Item[]) new Object[sizeR]);
    //loop to fill arrays itemsL and itemsR
    // fill left array
    for (int i = 0; i < sizeL; i++)
    {
      itemsL.set(i, items.get(i));
    }
    //fill right array
    for (int j = 0; j < sizeR; j++)
    {
      itemsR.set(j, items.get(sizeL + j));
    }

    //recursivley call the method on arrayL and arrayR
    this.sort(itemsL, comparator);
    this.sort(itemsR, comparator);

    // merge itemsL and itemsR

    int leftPointer = 0;
    int rightPointer = 0;
    int i = 0 ;
    //loop through until all either left array or right array has been fully transferred
    while (leftPointer < sizeL && rightPointer < sizeR)
    {

      //compare items in left array and right array and add smaller one to array,
      //if left item is smaller than (or equal to) right item
      //add left item to items and increment leftPointer
      if (comparator.compare(itemsL.get(leftPointer), itemsR.get(rightPointer)) <= 0)
      {
        items.set(i, itemsL.get(leftPointer));
        i++;
        leftPointer++;
      }else  // if right item is smaller than left item
      {
        items.set(i, itemsR.get(rightPointer));
        i++;
        rightPointer++;
      }
    }
    // add the remaining from either left array or right array
    //no comparator needed
    //left
    while (leftPointer < sizeL)
    {
      items.set(i, itemsL.get(leftPointer));
      i++;
      leftPointer++;
    }
    //right
    while (rightPointer < sizeR)
    {
      items.set(i, itemsR.get(rightPointer));
      i++;
      rightPointer++;
    }
  }
}
