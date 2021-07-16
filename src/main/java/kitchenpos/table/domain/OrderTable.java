package kitchenpos.table.domain;

import kitchenpos.tablegroup.domain.TableGroup;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_table")
public class OrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "table_group_id")
    private TableGroup tableGroup;
    private int numberOfGuests;
    private boolean empty;

    protected OrderTable() {
    }

    public OrderTable(Long id, TableGroup tableGroup, int numberOfGuests, boolean empty) {
        this.id = id;
        this.tableGroup = tableGroup;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public OrderTable(int numberOfGuests, boolean isEmpty) {
        this.numberOfGuests = numberOfGuests;
        this.empty = isEmpty;
        this.tableGroup = null;
    }

    public Long getId() {
        return id;
    }

    public TableGroup getTableGroup() {
        return tableGroup;
    }

    public Long getTableGroupId() {
        if (tableGroup == null) {
            return null;
        }
        return tableGroup.getId();
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void changeEmpty(boolean empty) {
        this.empty = empty;
    }

    public void updateTableGroup(TableGroup tableGroup) {
        this.empty = false;
        this.tableGroup = tableGroup;
    }

    protected void hasTableGroupIdCheck() {
        if (Objects.nonNull(tableGroup)) {
            throw new IllegalArgumentException("이미 단체 지정된 테이블이 있습니다.");
        }
    }

    protected void availableToUpdateCheck() {
        if (!empty) {
            throw new IllegalArgumentException("빈 주문 테이블이 아닙니다.");
        }
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }


    public void releaseGroup() {
        this.tableGroup = null;
    }

}
