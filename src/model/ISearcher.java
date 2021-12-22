package model;

public interface ISearcher {

    Solution search(ISearchable s);
    int getPointEvaluationAmount();
}
