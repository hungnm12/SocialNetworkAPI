package com.example.Social.Network.API.Model.Entity;

import com.example.Social.Network.API.Model.ResDto.PostResDto.ImageResDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Data
@Entity
@AllArgsConstructor
@Embeddable
public class Image implements List<ImageResDto> {
    @Id
    @Column(name = "image_id")
    private long id;

    @Column(name = "url_image")
    private String urlImage;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;
    public Image() {

    }

    public Image(String url, Post post1) {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @NotNull
    @Override
    public Iterator<ImageResDto> iterator() {
        return null;
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return null;
    }

    @Override
    public boolean add(ImageResDto imageResDto) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends ImageResDto> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends ImageResDto> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public ImageResDto get(int index) {
        return null;
    }

    @Override
    public ImageResDto set(int index, ImageResDto element) {
        return null;
    }

    @Override
    public void add(int index, ImageResDto element) {

    }

    @Override
    public ImageResDto remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @NotNull
    @Override
    public ListIterator<ImageResDto> listIterator() {
        return null;
    }

    @NotNull
    @Override
    public ListIterator<ImageResDto> listIterator(int index) {
        return null;
    }

    @NotNull
    @Override
    public List<ImageResDto> subList(int fromIndex, int toIndex) {
        return null;
    }
}
