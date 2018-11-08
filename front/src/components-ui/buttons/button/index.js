import React from 'react';
import styled from 'styled-components';
import {Colors} from '../../../helpers/style-variables';

const Content = styled.div`
	width: max-content;
	box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  padding: 12px 25px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  cursor: pointer;
  border-left: solid 3px transparent;

  &:hover {
    box-shadow: 0 4px 6px rgba(0,0,0,0.1);
    border-left: solid 3px ${Colors.MAIN_COLOR};
  }
`;

export const Button = ({children, onClick}) => (
  <Content onClick={onClick}>
    {children}
  </Content>
)
