import React from 'react';
import styled from "styled-components";
import {MediaViews} from "../../helpers/style-variables";

export const Title = ({children}) => <Content>{children}</Content>

const Content = styled.div`
  font-size: 1.3em;
  margin: 60px 0 40px 0;
  width: 100%;
  text-align: center;
  @media (max-width: ${MediaViews.MOBILE}px) {
    font-size: 1.1em;
  }
`;