package sankemao.framlib.http.response;

import java.util.List;

/**
 * Created by jin on 2017/7/22.
 *
 */
public class BaseResponse<T> {

    private boolean error;
    private List<T> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
