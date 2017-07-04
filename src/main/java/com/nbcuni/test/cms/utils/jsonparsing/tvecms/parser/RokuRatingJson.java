package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser;

import com.google.gson.annotations.SerializedName;
import com.nbcuni.test.webdriver.Utilities;

import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 10/23/15.
 */
public class RokuRatingJson {

    @SerializedName("scheme")
    private String scheme;

    @SerializedName("rating")
    private String rating;

    @SerializedName("subRatings")
    private List<String> subRatings;


    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<String> getSubRatings() {
        return subRatings;
    }

    public void setSubRatings(List<String> subRatings) {
        this.subRatings = subRatings;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((scheme == null) ? 0 : scheme.hashCode());
        result = prime * result
                + ((rating == null) ? 0 : rating.hashCode());
        result = prime * result
                + ((subRatings == null) ? 0 : subRatings.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RokuRatingJson other = (RokuRatingJson) obj;
        if (scheme == null) {
            if (other.scheme != null) {
                return false;
            }
        } else if (!scheme.equals(other.scheme)) {
            return false;
        }
        if (rating == null) {
            if (other.rating != null) {
                return false;
            }
        } else if (!rating.equals(other.rating)) {
            return false;
        }

        if (subRatings == null) {
            if (other.subRatings != null) {
                return false;
            }
        } else if (subRatings.size() != other.subRatings.size()
                ) {
            Utilities.logSevereMessage("The size of items lists are not same");
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RokuRatingJson{" +
                "scheme='" + scheme + '\'' +
                ", rating='" + rating + '\'' +
                ", subRatings=" + subRatings +
                '}';
    }
}
