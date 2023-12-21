package aoc.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import aoc.util.FileUtil;
import aoc.util.StringUtil;

public class Day19 extends Day {
    private static final int MAX_VALUE = 4000;
    private List<String> instructions;
    private List<Rating> ratings;

    protected void initialize() throws Exception {
        final List<List<String>> lists = FileUtil.readFileGroupByEmptyLine(getDay());
        instructions = lists.get(0);
        ratings = lists.get(1).stream().map(Rating::parse).collect(Collectors.toList());
    }

    protected Object getPart1Solution() {
        Map<String, List<Rule>> workflows = buildWorkflows(instructions);

        final Set<Rating> result = new HashSet<>();

        for (Rating rating : ratings) {
            String nextWorkflow = "in";

            while (true) {
                if (isAccepted(nextWorkflow)) {
                    result.add(rating);
                    break;
                } else if (isRejected(nextWorkflow)) {
                    break;
                }

                List<Rule> rules = workflows.get(nextWorkflow);
                for (Rule rule : rules) {
                    if (rule.isWorkflowOnly()) {
                        nextWorkflow = rule.getNextWorkflow();
                        break;
                    } else if (rule.isBooleanRule()) {
                        boolean test = rule.test(rating);
                        if (test) {
                            nextWorkflow = rule.getNextWorkflow();
                            break;
                        }
                    } else {
                        throw new RuntimeException();
                    }
                }
            }
        }

        return result.stream().mapToInt(Rating::getSum).sum();
    }

    protected Object getPart2Solution() {
        long result = 0;

        Map<String, List<Rule>> workflows = buildWorkflows(instructions);

        List<Rule> rulesWhichResultInAccepted = workflows.values().stream().flatMap(List::stream).filter(Rule::isOutcomeAccepted).collect(Collectors.toList());

        for (Rule rule : rulesWhichResultInAccepted) {
            result += getCombinationsToIn(rule, workflows);
        }

        return result;
    }

    private Map<String, List<Rule>> buildWorkflows(List<String> workflows) {
        final Map<String, List<Rule>> result = new HashMap<>();

        for (String workflow : workflows) {
            int indexOf = workflow.indexOf("{");
            String workflowName = workflow.substring(0, indexOf);

            String rulesString = workflow.substring(indexOf + 1, workflow.length() - 1);

            List<Rule> rules = new ArrayList<>();
            int i = 0;
            for (String rule : rulesString.split(",")) {
                rules.add(Rule.parse(rule, workflowName, i++));
            }
            result.put(workflowName, rules);
        }

        return result;
    }

    private long getCombinationsToIn(Rule rule, Map<String, List<Rule>> workflows) {
        List<String> appliedRules = new ArrayList<>();

        appliedRules.add(rule.getRule());

        while (true) {
            int ruleNumber = rule.ruleNumber;
            String workflow = rule.workflow;

            if (workflow.equals("in") && ruleNumber == 0) {
                break;
            }

            if (ruleNumber == 0) {
                rule = getRuleLeadingToWorkflow(workflow, workflows);
                appliedRules.add(rule.getRule());
                continue;
            }

            rule = workflows.get(workflow).get(ruleNumber - 1);
            appliedRules.add(rule.getOppositeRule());
        }

        final Map<Character, List<String>> collect = appliedRules.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(e -> e.charAt(0), Collectors.mapping(Function.identity(), Collectors.toList())));

        long result = 1;

        result *= getResult(collect.get('x'));
        result *= getResult(collect.get('m'));
        result *= getResult(collect.get('a'));
        result *= getResult(collect.get('s'));

        return result;
    }

    private long getResult(List<String> rules) {
        if (rules == null || rules.isEmpty()) {
            return MAX_VALUE;
        }

        boolean[][] tempArray = new boolean[rules.size()][MAX_VALUE];
        for (int i = 0; i < rules.size(); i++) {
            String newRule = rules.get(i);
            if (newRule.charAt(1) == '<') {
                Arrays.fill(tempArray[i], 0, Integer.parseInt(newRule.substring(2)) - 1, true);
            } else if (newRule.charAt(1) == '>') {
                Arrays.fill(tempArray[i], Integer.parseInt(newRule.substring(2)), MAX_VALUE, true);
            } else {
                throw new RuntimeException();
            }
        }
        return countTrue(mergeArray(tempArray));
    }

    private boolean[] mergeArray(boolean[][] tempArray) {
        if (tempArray.length == 1) {
            return tempArray[0];
        }
        boolean[] result = new boolean[tempArray[0].length];

        for (int i = 0; i < tempArray[0].length; i++) {
            boolean value = true;
            for (boolean[] booleans : tempArray) {
                value = value && booleans[i];
            }
            result[i] = value;
        }
        return result;
    }

    private long countTrue(boolean[] array) {
        long counter = 0;
        for (boolean b : array) {
            if (b) {
                counter++;
            }
        }
        return counter;
    }

    private Rule getRuleLeadingToWorkflow(String workflow, Map<String, List<Rule>> workflows) {
        for (Map.Entry<String, List<Rule>> stringListEntry : workflows.entrySet()) {
            Optional<Rule> rule = stringListEntry.getValue().stream().filter(r -> r.nextWorkflow.equals(workflow)).findFirst();
            if (rule.isPresent()) {
                return rule.get();
            }
        }
        throw new RuntimeException();
    }

    public boolean isAccepted(String nextWorkflow) {
        return "A".equals(nextWorkflow);
    }

    public boolean isRejected(String nextWorkflow) {
        return "R".equals(nextWorkflow);
    }

    private static class Rating {
        final int x;
        final int m;
        final int a;
        final int s;

        private Rating(int x, int m, int a, int s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
        }

        static Rating parse(String string) {
            final String[] split = string.split(StringUtil.DELIMITER_COMMA);

            int index = split[0].indexOf("=");
            int x = Integer.parseInt(split[0].substring(index + 1));

            index = split[1].indexOf("=");
            int m = Integer.parseInt(split[1].substring(index + 1));

            index = split[2].indexOf("=");
            int a = Integer.parseInt(split[2].substring(index + 1));

            index = split[3].indexOf("=");
            int s = Integer.parseInt(split[3].substring(index + 1, split[3].length() - 1));

            return new Rating(x, m, a, s);
        }

        public int getSum() {
            return x + m + a + s;
        }
    }

    private static class Rule implements Predicate<Rating> {
        private final String rule;
        private final String nextWorkflow;
        private final String workflow;
        private final int ruleNumber;

        private Rule(String rule, String nextWorkflow, String workflow, int ruleNumber) {
            this.rule = rule;
            this.nextWorkflow = nextWorkflow;
            this.workflow = workflow;
            this.ruleNumber = ruleNumber;
        }

        private static Rule parse(String string, String workflowName, int ruleNumber) {
            String[] split = string.split(":");
            if (split.length == 1) {
                return new Rule(null, split[0], workflowName, ruleNumber);
            } else if (split.length == 2) {
                return new Rule(split[0], split[1], workflowName, ruleNumber);
            }
            throw new RuntimeException();
        }

        public boolean isWorkflowOnly() {
            return rule == null;
        }

        public String getNextWorkflow() {
            return nextWorkflow;
        }

        public boolean isBooleanRule() {
            return rule.contains(">") || rule.contains("<");
        }

        public boolean isOutcomeAccepted() {
            return "A".equals(nextWorkflow);
        }

        @Override
        public boolean test(Rating rating) {
            int value;
            switch (rule.charAt(0)) {
                case 'x':
                    value = rating.x;
                    break;
                case 'm':
                    value = rating.m;
                    break;
                case 'a':
                    value = rating.a;
                    break;
                case 's':
                    value = rating.s;
                    break;
                default:
                    throw new RuntimeException();
            }
            if (rule.charAt(1) == '>') {
                return value > Integer.parseInt(rule.substring(2));
            } else if (rule.charAt(1) == '<') {
                return value < Integer.parseInt(rule.substring(2));
            }
            throw new RuntimeException();
        }

        public String getRule() {
            return rule;
        }

        public String getOppositeRule() {
            int value = Integer.parseInt(rule.substring(2));

            if (rule.charAt(1) == '>') {
                return rule.charAt(0) + "<" + (value + 1);
            } else if (rule.charAt(1) == '<') {
                return rule.charAt(0) + ">" + (value - 1);
            }
            throw new RuntimeException();
        }
    }
}
