import React, { Component } from "react";
import '../css/DataTable.css';

export default function Cell({
  height,
  content,
  header,
  fixed
}) {

  const fixedClass = fixed ? ' Cell-fixed' : '';
  const headerClass = header ? ' Cell-header' : '';
  // Add an inline style to adjust the height
  const style = height ? {height: `${height}px`} : undefined;

  let backgroundColor = "#FFFFFF"
  let text = content
  let textColor = '#000000'
  if(content[0] == "#") {
    backgroundColor = content
    text = "A"
    textColor = "transparent"
  }

  const className = (
    `Cell${fixedClass}${headerClass}`
  );

  const cellMarkup = header ? (
    <th className={className} style={style}>
      {text}
    </th>
  ) : (
    <td className={className} style={{height: `${height}px`, backgroundColor: `${backgroundColor}`, color: `${textColor}`}}>
      {text}
    </td>
  );

  return (cellMarkup);
}