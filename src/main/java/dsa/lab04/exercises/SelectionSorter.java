package dsa.lab04.exercises;

import dsa.lab02.base.StaticSequence;
import dsa.lab04.base.Sorter;
import dsa.lib.TODO;

import java.util.Comparator;

/**
 * Selection sort.
 */
public class SelectionSorter
  implements Sorter
{
  @Override
  public <Item> void sort(
    StaticSequence<Item> items,
    Comparator<Item> comparator)
  {
    //find minimum item
    //swap it into ith entry in array
    int size = items.size();
    for (int i = 0; i < size-1; i++)
    {
      int min = i;
      for (int j = i+1; j < size; j++)
      {
        if (comparator.compare(items.get(j), items.get(min)) < 0){
          min = j;
        }
      }
      items.swap (i,min);

    }



  }

}
