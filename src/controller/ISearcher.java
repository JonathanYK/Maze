package controller;

public interface ISearcher {

    Solution search(ISearchable s);
    int getPointEvaluationAmount();
}
