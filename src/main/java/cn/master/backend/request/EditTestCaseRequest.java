package cn.master.backend.request;

import cn.master.backend.entity.TestCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author create by 11's papa on 2022-12-01
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditTestCaseRequest extends TestCase {
    private List<String> follows;
}
