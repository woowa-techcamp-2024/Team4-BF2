package woowa.team4.bff.review.command;

public record ReviewMenuCreateCommand(String menuUuid, String content, Boolean isRecommend){

    public static ReviewMenuCreateCommand of(String menuUuid, String content, Boolean isRecommend) {
        return new ReviewMenuCreateCommand(menuUuid, content, isRecommend);
    }

}
