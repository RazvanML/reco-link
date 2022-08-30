---
layout: post
title: Starting with Rust
tags: Rust VisualStudioCode HelloWorld Quicksort
categories: Rust
---


Installing Rust on Windows was quick and easy. I've just ran the <a href="https://www.rust-lang.org/tools/install">installation EXE of Rust</a>, downloaded and installed Visual Studio Codem and inside Visual Studio Code, installed the Rust plugin.


Giving a try for Quicksort:

```Rust
use std::vec;

fn main() {
    let mut vec1 = vec![2,2, 2, 2];
    sort(&mut vec1);
    for  x in vec1 {
        print!("{}",x);
    }
}


fn sort_size(vec: &mut Vec<i32>, start : usize, stop:usize  )  {
    if stop-start <= 1 {
      return;
    }
    let pivot = vec[start];
    let mut top = start;
    let mut bottom = stop;
    for i in start+1..stop {
        if vec[i] < pivot {
         vec[top] = vec[i];
         top+=1;
        }
        if vec[i] > pivot {
            bottom-=1;
            vec[bottom] = vec[i];
           }
       }
    for n in top..bottom {
        vec[n] = pivot;
    }
    sort_size(vec, start,top);
    sort_size(vec, bottom, stop);
}

fn sort(vec: &mut Vec<i32> )  {
    return sort_size(vec, 0, vec.len());
}


mod tests {
    use super::sort;

    #[test]
    fn empty() {
    let mut vec1:Vec<i32>  = vec![];
    sort(&mut vec1);
    assert_eq!(vec1.len(),0);
    }
    #[test]
    fn one() {
    let mut vec1  = vec![4];
    sort(&mut vec1);
    let vec2  = vec![4];
    assert_eq!(vec1,vec2);
    }    

    /// . Larger example, with repeating pivot 
    #[test]
    fn large() {
        let mut vec1  = vec![5,3,5,9,5,10,2];
        sort(&mut vec1);
        let vec2  = vec![2,3,5,5,5,9,10];
        assert_eq!(vec1,vec2);
        }    
    
}
```

Upon failing the test, the need to debug the code becomes evident. Here is where I lost interest for IntelliJ Community version. Getting debugging capability would require the paid version of it, or paid version of CLion.

Clearly the partitioning function was buggy, here is the version after debugging:

```Rust
use std::vec;

fn main() {
    let mut vec1 = vec![2,2, 2, 2];
    sort(&mut vec1);
    for  x in vec1 {
        print!("{}",x);
    }
}


fn sort_size(vec: &mut Vec<i32>, start : usize, stop:usize  )  {
    if stop-start <= 1 {
      return;
    }
    let pivot = vec[start];
    let mut top = start;
    let mut bottom = stop;
    'main: for i in start+1..stop {
        if i >= bottom {break}
        if vec[i] < pivot {
            vec[top] = vec[i]; 
            top+=1;
        }
        if vec[i] > pivot {
            loop {
                bottom-=1;
                if bottom == i {break 'main}
                if vec[bottom] <= pivot {break}
            }; 
            let x = vec[bottom];
            vec[bottom] = vec[i];
            if x < pivot {
                vec[top] = x;
                top+=1;
            }
        }
    }
    for n in top..bottom {
        vec[n] = pivot;
    }
    sort_size(vec, start,top);
    sort_size(vec, bottom, stop);
}

fn sort(vec: &mut Vec<i32> )  {
    return sort_size(vec, 0, vec.len());
}


mod tests {
    use super::sort;

    #[test]
    fn empty() {
    let mut vec1:Vec<i32>  = vec![];
    sort(&mut vec1);
    assert_eq!(vec1.len(),0);
    }
    #[test]
    fn one() {
    let mut vec1  = vec![4];
    sort(&mut vec1);
    let vec2  = vec![4];
    assert_eq!(vec1,vec2);
    }    

    /// . Larger example, with repeating pivot 
    #[test]
    fn large() {
        let mut vec1  = vec![5,3,5,9,5,10,2];
        sort(&mut vec1);
        let vec2  = vec![2,3,5,5,5,9,10];
        assert_eq!(vec1,vec2);
        }    

    #[test]
    fn sorted() {
        let mut vec1  = vec![1,2];
        sort(&mut vec1);
        let vec2  = vec![1,2];
        assert_eq!(vec1,vec2);
    }  

    #[test]
    fn rev() {
        let mut vec1  = vec![2,1];
        sort(&mut vec1);
        let vec2  = vec![1,2];
        assert_eq!(vec1,vec2);
    }      

    #[test]
    fn swap() {
        let mut vec1  = vec![5,10,4];
        sort(&mut vec1);
        let vec2  = vec![4,5,10];
        assert_eq!(vec1,vec2);
    }  

    #[test]
    fn equal() {
        let mut vec1  = vec![1,1,1];
        sort(&mut vec1);
        let vec2  = vec![1,1,1];
        assert_eq!(vec1,vec2);
    }      
}  
```

My objective was to partition in place. The code breaks the vector in three partitions: all the elements smaller than the pivot at start, the elements larger than the pivot at the end of the vector and the middle, the elements equal to the pivot. It quarantes that the produced partitions are always smaller and we don't run in an infinite loop.

The algorithm is supposed to be <a href="https://en.wikipedia.org/wiki/Cache-oblivious_algorithm"> catche oblivious</a>. At any point there are three active memory pages.
