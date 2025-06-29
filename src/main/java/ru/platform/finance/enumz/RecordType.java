package ru.platform.finance.enumz;

import lombok.Getter;

/**
 * Типы операций в таблице записей балансов бустера
 * SALARY - зарплата бустера за заказ
 * TIP - чаевые
 * WITHDRAWAL - вывод средств на счет
 */
@Getter
public enum RecordType {
    SALARY,
    WITHDRAWAL,
    TIP
}
