package com.luobi.study.project.common.utils;

import com.luobi.study.project.common.model.PageData;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.lang.NonNull;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class ConvertUtils {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static <T, S> T convert(S source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        return modelMapper.map(source, clazz);
    }

    @NonNull
    public static <T, S> T convertNonNull(@NonNull S source, Class<T> clazz) {
        return modelMapper.map(source, clazz);
    }

    @NonNull
    public static <T, S> T convertNonNull(@NonNull S source, Type type) {
        return modelMapper.map(source, type);
    }

    public static <T, S> T convert(S source, Type type) {
        if (source == null) {
            return null;
        }
        return modelMapper.map(source, type);
    }

    public static <T, S> void copyProperties(@NonNull S source, @NonNull T target) {
        modelMapper.map(source, target);
    }

    public static <T, S> List<T> convertToList(List<S> source, Class<T> clazz) {
        return convertToList(source, clazz, item -> convert(item, clazz));
    }

    public static <T, S> List<T> convertToList(List<S> source, Class<T> clazz, Function<S, T> mapper) {
        return Optional.ofNullable(source).orElse(Collections.emptyList()).stream().map(mapper).filter(
                Objects::nonNull).collect(toList());
    }

    public static <T, S> PageData<T> convertPage(@NonNull PageData<S> source, Class<T> clazz) {
        return new PageData<>(convertToList(source.getData(), clazz), source.getPageRequest(), source.getTotal());
    }

    public static <T, S> PageData<T> convertPage(@NonNull PageData<S> source, Class<T> clazz, Function<S, T> mapper) {
        return new PageData<>(convertToList(source.getData(), clazz, mapper), source.getPageRequest(),
                source.getTotal());
    }

}
