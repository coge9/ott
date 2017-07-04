package com.nbcuni.test.cms.utils.sortcontent;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.pageobjectutils.entities.rules.OrderType;
import com.nbcuni.test.cms.pageobjectutils.entities.rules.SortingType;
import com.nbcuni.test.cms.utils.comparator.LongComparator;
import com.nbcuni.test.cms.utils.comparator.StringIgnoreCaseComparator;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ivan_Karnilau on 5/19/2017.
 */
public class LatestVideos {

    private LatestVideos() {
        super();
    }

    public static List<GlobalVideoEntity> getPublishedVideos(String brand, Collection<String> programs, SortingType sortBy, OrderType order, int limit) {
        List<GlobalVideoEntity> publishedVideos = new NodeApi(brand).getVideos().stream()
                .filter(video -> hasParentProgram(video.getMpxAsset().getSeriesTitle(), programs))
                .collect(Collectors.toCollection(LinkedList::new));
        publishedVideos = publishedVideos.stream()
                .sorted(getGlobalVideoEntityComparator(sortBy, order)).collect(Collectors.toCollection(LinkedList::new));

        publishedVideos = publishedVideos.stream().limit(limit).collect(Collectors.toCollection(LinkedList::new));

        return publishedVideos;
    }

    private static Comparator<GlobalVideoEntity> getGlobalVideoEntityComparator(SortingType sortBy, OrderType order) {
        return (o1, o2) -> {
            switch (sortBy) {
                case TITLE: {
                    Comparator<String> comparator = new StringIgnoreCaseComparator();
                    return getReversedIfRequired(comparator, order).compare(o1.getTitle(), o2.getTitle());
                }
                case AIR_DATE: {
                    Comparator<Long> comparator = new LongComparator();
                    return getReversedIfRequired(comparator, order).compare(o1.getMpxAsset().getPubDate(), o2.getMpxAsset().getPubDate());
                }
                default: {
                    Comparator<Long> comparator = new LongComparator();
                    return getReversedIfRequired(comparator, order).compare(o1.getMpxAsset().getPubDate(), o2.getMpxAsset().getPubDate());
                }
            }
        };
    }

    private static Comparator getReversedIfRequired(Comparator comparator, OrderType orderType) {
        if (orderType.equals(OrderType.DESC)) {
            return comparator.reversed();
        } else {
            return comparator;
        }
    }

    public static List<GlobalVideoEntity> getPublishedVideos(String brand, SortingType sortBy, OrderType order, int limit) {
        return getPublishedVideos(brand, new NodeApi(brand).getAllPublishedPrograms().getEntity().values(), sortBy, order, limit);
    }

    public static List<GlobalProgramEntity> getPublishedPrograms(String brand, OrderType order, int limit) {
        List<GlobalProgramEntity> publishedPrograms = new NodeApi(brand).getPrograms();

        publishedPrograms = publishedPrograms.stream()
                .sorted((o1, o2) -> {
                    Comparator<String> comparator = new StringIgnoreCaseComparator();
                    if (order.equals(OrderType.DESC)) {
                        return comparator.reversed().compare(o1.getTitle(), o2.getTitle());
                    } else {
                        return comparator.compare(o1.getTitle(), o2.getTitle());
                    }
                }).collect(Collectors.toList());

        publishedPrograms = publishedPrograms.stream().limit(limit).collect(Collectors.toList());

        return publishedPrograms;
    }

    private static boolean hasParentProgram(String parentProgram, Collection<String> programs) {
        if (parentProgram == null) {
            return false;
        }
        for (String program : programs) {
            if (program.equals(parentProgram)) {
                return true;
            }
        }
        return false;
    }
}
