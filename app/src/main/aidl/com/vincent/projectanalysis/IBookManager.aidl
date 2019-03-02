// IBookManager.aidl
package com.vincent.projectanalysis;

import  com.vincent.projectanalysis.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
