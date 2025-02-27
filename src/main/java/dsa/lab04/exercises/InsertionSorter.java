package dsa.lab04.exercises;

import dsa.lab02.base.StaticSequence;
import dsa.lab04.base.Sorter;
import dsa.lib.TODO;

import java.util.Comparator;

/**
 * Insertion sort.
 */
public class InsertionSorter
  implements Sorter
{
  @Override
  public <Item> void sort(
    StaticSequence<Item> items,
    Comparator<Item> comparator)
  {
    /*
    for (int i = 1; i < items.size() ; i++)
    {
      while ( i > 0 && comparator.compare(items.get(i), items.get(i - 1) )< 0 )
      {
        items.set(i, items.get(i - 1));
        i--;

      }
    } */

    for (int i = 1; i < items.size(); i++)
    {
      int j = i;
      Item tmp = items.get(i);
      // while comparator result is less than 0
      // while item 1 is less than item 2 comparator result is -ve
      // if temp is larger than items.get(j-1) then move items.get(j-1) to position j
      while (j > 0 && comparator.compare(tmp, items.get(j-1)) < 0)
      {
        items.set(j, items.get(j-1));
        j--;
      }
      items.set(j, tmp);
    }
  }
}
